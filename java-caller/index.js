
import { JavaCaller } from "java-caller";


async function main(){
    const java = new JavaCaller({
        jar: './chuggu-me.jar',
    });
    const { status, stdout, stderr } = await java.run();
    console.log(status)
    console.log(stdout)
    console.log(stderr)
}

main();

