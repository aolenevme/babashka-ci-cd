const core = require('@actions/core');
const github = require('@actions/github');

function action() {
    try {
        const titleRegexInput = core.getInput('title-regex');
        if (!isTitleValid(titleRegexInput)) {
            core.setFailed(
                `The title of the pull request does not match regex pattern "${titleRegexInput}".`,
            )
        }

        if (!isDescriptionNotEmpty()) {
            core.setFailed(
                `Pull request description is empty.`,
            )
        }

        console.log("The PR`s title and description are valid!")
    } catch (error) {
        core.setFailed(error.message);
    }
}

function isTitleValid(titleRegexInput) {
    const titleRegex = new RegExp(titleRegexInput);
    const title =
        github.context.payload &&
        github.context.payload.pull_request &&
        github.context.payload.pull_request.title;

    return titleRegex.test(title)
}

function isDescriptionNotEmpty() {
    const pullRequestDescription =
        github.context.payload &&
        github.context.payload.pull_request &&
        github.context.payload.pull_request.body;

    return typeof pullRequestDescription === "string" && pullRequestDescription.length > 0;
}

action();
