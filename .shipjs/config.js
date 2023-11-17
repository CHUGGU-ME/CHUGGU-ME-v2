module.exports = {
    mainVersionFile: 'package.json',
    versionUpdated: ({ version, exec }) => exec(`git commit -am "chore: release v${version}"`),
  };