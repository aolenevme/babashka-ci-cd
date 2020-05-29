const core = require('@actions/core');
const github = require('@actions/github');

try {
    const titleRegexInput = core.getInput('title-regex')
    const titleRegex = new RegExp(titleRegexInput)
    console.log(github.context.payload.pull_request.body);
    const title =
        github.context.payload &&
        github.context.payload.pull_request &&
        github.context.payload.pull_request.title

    const isValid = titleRegex.test(title)

    if (!isValid) {
        core.setFailed(
            `Pull request title "${title}" does not match regex pattern "${titleRegex}".`,
        )
    }
} catch (error) {
    core.setFailed(error.message);
}
