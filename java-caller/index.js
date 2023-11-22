#!/usr/bin/env node
const yargs = require('yargs')
const JavaCaller = require('java-caller');
const java = new JavaCaller.JavaCaller({
    jar: './deploy/CHUGGU-ME-v2.jar',
});

java.run(yargs.argv._).then(({ status, stdout, stderr })=>{
    console.log(stdout)
}).catch(({  stdout, stderr })=>{
    console.log(stderr)
});
