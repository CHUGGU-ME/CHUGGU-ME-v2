#!/usr/bin/env node
const yargs = require('yargs');
const path = require('path');
const jarPath = path.resolve(__dirname, './deploy/CHUGGU-ME-v2.jar');
const JavaCaller = require('java-caller');
const java = new JavaCaller.JavaCaller({
    jar: jarPath,
});

java.run(yargs.argv._).then(({ status, stdout, stderr })=>{
    console.log(stdout)
}).catch(({  stdout, stderr })=>{
    console.log(stderr)
});
