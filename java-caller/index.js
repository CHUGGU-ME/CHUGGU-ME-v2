#!/usr/bin/env node
const yargs = require('yargs');
const path = require('path');
const JavaCaller = require('java-caller');
const java = new JavaCaller.JavaCaller({
    jar: 'deploy/CHUGGU-ME-v2.jar',
    rootPath: __dirname
});

java.run(yargs.argv._).then(({ status, stdout, stderr })=>{
    console.log(stdout)
    console.log(stderr, status)
}).catch(({  stdout, stderr })=>{
    console.log(stderr)
});