import { readFileSync, writeFileSync, promises, constants, readdirSync, statSync, existsSync } from 'fs';
import { resolve, sep, join } from 'path';
import { prerelease, inc, valid, patch, minor } from 'semver';
import shell from 'shelljs';
import gh from 'parse-github-url';
import merge from 'deepmerge';

function getCurrentVersion(
  dir = '.',
  filename = 'package.json'
) {
  const { version } = JSON.parse(readFileSync(resolve(dir, filename)));
  return version;
}

const GIT_COMMIT_PREFIX_PATCH = new Set([
  'fix',
  'docs',
  'style',
  'refactor',
  'perf',
  'test',
  'chore',
  'ci',
]);

const GIT_COMMIT_PREFIX_MINOR = new Set(['feat']);

const GIT_COMMIT_BREAKING_CHANGE = 'BREAKING CHANGE';

function exec(
  command,
  { dir = '.', ignoreError = false, silent = false } = {}
) {
  const result = shell.exec(command, { silent, cwd: dir });
  if (result.code === 0) {
    return result;
  } else if (ignoreError) {
    return {
      toString: () => '',
      code: result.code,
    };
  } else {
    throw new Error(
      JSON.stringify(
        {
          message: `Exit code is ${result.code}`,
          command,
          result,
        },
        null,
        2
      )
    );
  }
}

function silentExec(command, opts = {}) {
  return exec(command, {
    ...opts,
    silent: true,
  });
}

function getCommitTitles(revisionRange, dir) {
  const cmd = `git log ${revisionRange} --pretty=format:%s`;
  return silentExec(cmd, { dir, ignoreError: true }).toString().trim();
}

function getBodies(revisionRange, dir) {
  const cmd = `git log ${revisionRange} --pretty=format:%b`;
  return silentExec(cmd, { dir, ignoreError: true }).toString().trim();
}

function getCommitNumbersPerType(commitTitles) {
  const ignoredMessages = [];
  const numbers = {};
  commitTitles.split('\n').forEach((rawTitle) => {
    const title = rawTitle.trim();
    if (!title) {
      return;
    }
    if (title.startsWith('Merge branch')) {
      return;
    }
    const match = title.match(/(.*?)(\(.*?\))?:.*/);
    if (!match || !match[1]) {
      ignoredMessages.push(title);
      return;
    }
    const prefix = match[1].toLowerCase();
    if (
      GIT_COMMIT_PREFIX_PATCH.has(prefix) ||
      GIT_COMMIT_PREFIX_MINOR.has(prefix)
    ) {
      numbers[prefix] = numbers[prefix] || 0;
      numbers[prefix] += 1;
    } else {
      ignoredMessages.push(title);
    }
  });
  return { numbers, ignoredMessages };
}

function getNextVersionFromCommitMessages(version, titles, bodies) {
  if (prerelease(version)) {
    return { version: inc(version, 'prerelease') };
  }
  if (
    bodies
      .toUpperCase()
      .split('\n')
      .some((line) => line.startsWith(GIT_COMMIT_BREAKING_CHANGE))
  ) {
    return { version: inc(version, 'major') };
  }
  const { numbers, ignoredMessages } = getCommitNumbersPerType(titles);
  const minor = Array.from(GIT_COMMIT_PREFIX_MINOR).some(
    (prefix) => numbers[prefix] > 0
  );
  const patch = Array.from(GIT_COMMIT_PREFIX_PATCH).some(
    (prefix) => numbers[prefix] > 0
  );
  if (minor) {
    return { version: inc(version, 'minor'), ignoredMessages };
  } else if (patch) {
    return { version: inc(version, 'patch'), ignoredMessages };
  } else if (titles.trim().length === 0) {
    return { version: null, ignoredMessages };
  } else {
    return { version: inc(version, 'patch'), ignoredMessages };
  }
}

function getNextVersion(
  revisionRange,
  currentVersion,
  dir = '.'
) {
  const titles = getCommitTitles(revisionRange, dir);
  const bodies = getBodies(revisionRange, dir);
  return getNextVersionFromCommitMessages(currentVersion, titles, bodies);
}

function updateVersion({
  nextVersion,
  dir = '.',
  fileName = 'package.json',
}) {
  const filePath = resolve(dir, fileName);
  const json = JSON.parse(readFileSync(filePath).toString());
  json.version = nextVersion;
  writeFileSync(filePath, `${JSON.stringify(json, null, 2)}\n`);
}

function isValidVersion(version) {
  return valid(version);
}

function getReleaseTag(version) {
  const [releaseTag] = prerelease(version) || [];
  return releaseTag || 'latest';
}

function getReleaseType(nextVersion) {
  if (prerelease(nextVersion) !== null) {
    return 'prerelease';
  }

  if (patch(nextVersion) === 0) {
    if (minor(nextVersion) === 0) {
      return 'major';
    } else {
      return 'minor';
    }
  } else {
    return 'patch';
  }
}

function hasLocalBranch(branchName, dir = '.') {
  return (
    silentExec(`git show-ref --verify --quiet refs/heads/${branchName}`, {
      dir,
      ignoreError: true,
    }).code === 0
  );
}

function hasRemoteBranch(remote, branchName, dir = '.') {
  return (
    silentExec(`git ls-remote --heads ${remote} ${branchName}`, { dir })
      .toString()
      .trim() !== ''
  );
}

function getCurrentBranch(dir = '.') {
  return silentExec('git rev-parse --abbrev-ref HEAD', { dir })
    .toString()
    .trim();
}

function getLatestCommitMessage(dir = '.') {
  return silentExec('git log -1 --pretty=format:%B', { dir }).toString().trim();
}

function getRemoteOriginUrl(remote, dir) {
  const url = silentExec(`git remote get-url ${remote}`, { dir })
    .toString()
    .trim();
  return url;
}

function getRepoInfo(remote, dir) {
  const remoteOriginalUrl = getRemoteOriginUrl(remote, dir);
  const { owner, name, repo, branch } = gh(remoteOriginalUrl);
  const url = `https://github.com/${repo}`;

  return {
    owner, // "algolia"
    name, // "shipjs"
    repo, // "algolia/shipjs"
    branch, // "master"
    url, // "https://github.com/algolia/shipjs"
  };
}

function hasRemote(remote, dir = '.') {
  return (
    silentExec(`git remote get-url ${remote}`, {
      dir,
      ignoreError: true,
    }).code === 0
  );
}

function getRepoURLWithToken(token, remote, dir) {
  const url = getRemoteOriginUrl(remote, dir);
  const { repo } = gh(url);
  return `https://${token}@github.com/${repo}`;
}

function getRepoURLWithTokenMasked(remote, dir) {
  const url = getRemoteOriginUrl(remote, dir);
  const { repo } = gh(url);
  return `https://xxx@github.com/${repo}`;
}

function getLatestCommitHash(dir = '.') {
  return silentExec('git log -1 --pretty=format:%h', { dir }).toString().trim();
}

function getCommitUrl(remote, hash, dir = '.') {
  const { url: repoURL } = getRepoInfo(remote, dir);
  return `${repoURL}/commit/${hash}`;
}

function isWorkingTreeClean(dir = '.') {
  return silentExec('git status --porcelain', { dir }).toString().trim() === '';
}

function hasTag(tag, dir = '.') {
  return silentExec(`git tag -l ${tag}`, { dir }).toString().trim() === tag;
}

/*
$ git branch -r
  origin/HEAD -> origin/master
  origin/chore/all-contributors
  origin/master
  origin/renovate/pin-dependencies
  origin/renovate/rollup-1.x
  ...
*/

function getRemoteBranches(dir = '.') {
  const origins = silentExec('git remote', { dir })
    .toString()
    .trim()
    .split('\n');
  return silentExec('git branch -r', { dir })
    .toString()
    .trim()
    .split('\n')
    .map((line) => line.trim())
    .filter((line) => !line.includes(' -> '))
    .map((line) => {
      const origin = origins.find((_origin) => line.startsWith(`${_origin}/`));
      return line.slice(origin.length + 1);
    })
    .filter(Boolean);
}

function getGitConfig(key, dir = '.') {
  return silentExec(`git config ${key}`, { dir, ignoreError: true })
    .toString()
    .trim();
}

var shipJsVersion = '0.26.3';

var defaultConfig = {
  remote: 'origin',
  // monorepo: {
  //   mainVersionFile: 'package.json',
  //   packagesToBump: ['packages/*', 'examples/*'],
  //   packagesToPublish: ['packages/*'],
  // },
  shouldPrepare: undefined, // async ({ commits, nextVersion, releaseType, releaseTag, commitNumbersPerType }) => {},
  updateChangelog: true,
  conventionalChangelogArgs: '-p angular -i CHANGELOG.md -s',
  installCommand: ({ isYarn }) =>
    isYarn ? 'yarn install --silent' : 'npm install',
  versionUpdated: undefined, // ({ version, releaseType, dir, exec }) => {},
  getNextVersion: undefined, // ({ revisionRange, commitTitles, commitBodies, currentVersion, dir }) => {},
  beforeCommitChanges: undefined, // ({ nextVersion, releaseType, exec, dir }) => {},
  getStagingBranchName: ({ nextVersion, releaseType }) =>
    `releases/v${nextVersion}`,
  formatCommitMessage: ({ version, releaseType, baseBranch }) =>
    `chore: release v${version}`,
  formatPullRequestTitle: ({ version, releaseType }) =>
    `chore: release v${version}`,
  formatPullRequestMessage: ({
    repo, // "instantsearch.js"
    repoURL, // "https://github.com/algolia/instantsearch.js"
    baseBranch, // "master"
    stagingBranch, // "releases/v4.5.0"
    destinationBranch, // "master"
    releaseType, // "major" | "minor" | "patch" | "prerelease"
    diffURL,
    currentVersion,
    nextVersion,
    publishCommands, // either a string or an array (if monorepo)
    title,
  }) => {
    const repoLink = `[${repo}](${repoURL})`;
    const diffLink = `[\`${currentVersion}\` → \`${nextVersion}\`](${diffURL})`;
    const publishCommandsTable =
      typeof publishCommands === 'string'
        ? `\`\`\`${publishCommands}\`\`\``
        : `
| Dir | Command |
|---|---|
${publishCommands
  .map(({ dirName, command }) => `| ${dirName} | ${command} |`)
  .join('\n')}
`.trim();

    const mergeInstruction = `
When merging this pull request, you need to **Squash and merge** and make sure that the title starts with \`${title}\`.
<details>
<summary>See details</summary>

After that, a commit \`${title}\` will be added and you or your CI can run \`shipjs trigger\` to trigger the release based on the commit.
![Squash and merge](https://raw.githubusercontent.com/algolia/shipjs/v${shipJsVersion}/assets/squash-and-merge.png)
</details>
    `.trim();

    const message = `
This pull request prepares the following release:
| Repository | Branch | Update | Change |
|---|---|---|---|
| ${repoLink} | ${stagingBranch} | ${releaseType} | ${diffLink} |

### Release Summary
This is going to be published with the following command:

${publishCommandsTable}

### Merging Instructions
${mergeInstruction}

---

_This pull request is automatically generated by [Ship.js](https://github.com/algolia/shipjs)_.
`.trim();
    return message;
  },
  draftPullRequest: false,
  pullRequestReviewers: undefined,
  pullRequestTeamReviewers: undefined,
  shouldRelease: ({
    commitMessage,
    currentVersion,
    currentBranch,
    formatPullRequestTitle,
  }) => {
    if (currentBranch === `releases/v${currentVersion}`) {
      return [
        'It seems you ran `shipjs trigger` inside a release pull-request.',
        'Merge this pull-request first, then run it again on your base branch.',
      ].join('\n');
    }

    const correctCommitMessage = formatPullRequestTitle({
      version: currentVersion,
    });
    if (!commitMessage.trim().startsWith(correctCommitMessage)) {
      return (
        'The commit message should have started with the following:' +
        '\n' +
        `${correctCommitMessage}`
      );
    }
    return true;
  },
  buildCommand: ({ isYarn }) => (isYarn ? 'yarn build' : 'npm run build'),
  beforePublish: undefined, // ({ exec, dir }) => {}
  publishCommand: ({ isYarn, tag, defaultCommand, dir }) => defaultCommand,
  afterPublish: undefined, // ({ exec, dir, version, releaseTag }) => {}
  getTagName: ({ version }) => `v${version}`,
  testCommandBeforeRelease: undefined, // ({ isYarn }) => isYarn ? 'yarn test' : 'npm run test',
  appName: undefined,
  forcePushBranches: [],
  slack: {
    default: {
      username: 'Ship.js',
      // eslint-disable-next-line camelcase
      icon_emoji: ':passenger_ship:',
    },
    prepared: ({ appName, version, pullRequestUrl }) => ({
      pretext: `:writing_hand: The release for *${appName}@${version}* is prepared!`,
      fields: [
        {
          title: 'Version',
          value: version,
          short: true,
        },
        {
          title: 'Pull Request',
          value: pullRequestUrl,
          short: false,
        },
      ],
    }),
    releaseSuccess: ({
      appName,
      version,
      tagName,
      latestCommitHash,
      latestCommitUrl,
      repoURL,
    }) => ({
      pretext: `:tada: Successfully released *${appName}@${version}*`,
      fields: [
        {
          title: 'Commit',
          value: `*<${latestCommitUrl}|${latestCommitHash}>*`,
          short: true,
        },
        {
          title: 'Version',
          value: version,
          short: true,
        },
        {
          title: 'Release',
          value: `${repoURL}/releases/tag/${tagName}`,
        },
      ],
    }),
  },
  releases: {
    assetsToUpload: [],
    extractChangelog: undefined, // ({ version, dir }) => `some specific changelog to that version`,
  },
};

function mergeConfig(config1, config2) {
  const config = {
    ...config1,
    ...config2,
  };
  config.slack = merge(config1.slack || {}, config2.slack || {});
  return config;
}

const exist = (path) =>
  promises
    .access(path, constants.F_OK)
    .then(() => path)
    .catch(() => false);

const pickDefault = (obj) =>
  typeof obj.default === 'object' ? obj.default : obj;

async function loadConfig(
  dir = '.',
  filename = 'ship.config',
  extensions = ['js', 'cjs', 'mjs']
) {
  const fullPaths = extensions.map((ext) => resolve(dir, `${filename}.${ext}`));

  const fullPath = (
    await Promise.all(fullPaths.map((path) => exist(path)))
  ).find((path) => path);

  const userConfig =
    fullPath && (await exist(fullPath))
      ? await import(fullPath).then(pickDefault)
      : {};

  return mergeConfig(defaultConfig, userConfig);
}

async function getAppName(dir = '.') {
  const { appName } = await loadConfig(dir);
  if (appName) {
    return appName;
  }
  const { name } = JSON.parse(await promises.readFile(resolve(dir, 'package.json')));
  return name;
}

const isDirectory = (dir) => statSync(dir).isDirectory();
const getDirectories = (dir) =>
  readdirSync(dir)
    .map((name) => join(dir, name))
    .filter(isDirectory);
const hasPackageJson = (dir) => existsSync(`${dir}/package.json`);
const flatten = (arr) => arr.reduce((acc, item) => acc.concat(item), []);

function expandPackageList(list, dir = '.') {
  return flatten(
    list.map((item) => {
      const partIndex = item
        .split(sep)
        .findIndex((part) => part.startsWith('@(') && part.endsWith(')'));
      if (partIndex !== -1) {
        const parts = item.split(sep);
        const part = parts[partIndex];
        const newList = part
          .slice(2, part.length - 1)
          .split('|')
          .map((subPart) => {
            const newParts = [...parts];
            newParts[partIndex] = subPart;
            return newParts.join(sep);
          });
        return expandPackageList(newList, dir);
      }

      if (item.endsWith('/*')) {
        const basePath = resolve(dir, item.slice(0, item.length - 2));
        return expandPackageList(getDirectories(basePath), dir);
      } else {
        return resolve(dir, item);
      }
    })
  ).filter(hasPackageJson);
}

export { exec, expandPackageList, getAppName, getBodies as getCommitBodies, getCommitNumbersPerType, getCommitTitles, getCommitUrl, getCurrentBranch, getCurrentVersion, getGitConfig, getLatestCommitHash, getLatestCommitMessage, getNextVersion, getReleaseTag, getReleaseType, getRemoteBranches, getRepoInfo, getRepoURLWithToken, getRepoURLWithTokenMasked, hasLocalBranch, hasRemote, hasRemoteBranch, hasTag, isValidVersion, isWorkingTreeClean, loadConfig, silentExec, updateVersion };
