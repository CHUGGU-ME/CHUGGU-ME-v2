package authors

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class AuthorSubCommand : Subcommand("author", "show authors") {

    lateinit var authorOutputView: AuthorOutputView
    override fun execute() {
        authorOutputView = AuthorOutputView()
        authorOutputView.authorPrint()
    }
}