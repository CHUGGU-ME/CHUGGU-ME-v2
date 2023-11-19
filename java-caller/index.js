
import { JavaCaller } from "java-caller";


async function main(){
    const java = new JavaCaller({
        jar: './CHUGGU-ME-v2-1.0-SNAPSHOT.jar',
    });
    const { status, stdout, stderr } = await java.run();
    console.log(status)
    console.log(stdout)
    // console.log(stderr)
}

main();

