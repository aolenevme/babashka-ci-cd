const core = require('@actions/core');
const github = require('@actions/github');

function action() {
    try {
        if (!isTitleValid()) {
            core.setFailed(
                `Pull request title "${title}" does not match regex pattern "${titleRegex}".`,
            )
        }

        if (!isDescriptionNotEmpty()) {
            core.setFailed(
                `Pull request description is empty.`,
            )
        }
    } catch (error) {
        core.setFailed(error.message);
    }
}

function isTitleValid() {
    const titleRegexInput = core.getInput('title-regex');
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
