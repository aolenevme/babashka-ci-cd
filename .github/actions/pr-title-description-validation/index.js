"use strict";

const core = require("@actions/core");
const github = require("@actions/github");

function isTitleValid(titleRegexInput) {
    const titleRegex = new RegExp(titleRegexInput, "u");
    const title
        = github.context.payload
        && github.context.payload.pull_request
        && github.context.payload.pull_request.title;

    return titleRegex.test(title);
}

function isTitleStartedWithRefName(refName) {
    const title
        = github.context.payload
        && github.context.payload.pull_request
        && github.context.payload.pull_request.title;

    return typeof title === "string"
        && typeof refName === "string"
        && title.startsWith(refName);
}

function isDescriptionNotEmpty() {
    const pullRequestDescription
        = github.context.payload
        && github.context.payload.pull_request
        && github.context.payload.pull_request.body;

    return typeof pullRequestDescription === "string"
        && pullRequestDescription.length > 0;
}

function action() {
    try {
        const titleRegexInput = core.getInput("title-regex");

        if (!isTitleValid(titleRegexInput)) {
            core.setFailed(
                // eslint-disable-next-line max-len
                `The title of the pull request does not match regex pattern "${titleRegexInput}".`
            );
        }

        const refName = github.context.payload.pull_request.head.ref;

        if (!isTitleStartedWithRefName(refName)) {
            core.setFailed(
                `The title of the pull request has to start with ${refName}`
            );
        }

        if (!isDescriptionNotEmpty()) {
            core.setFailed(
                `Pull request description is empty.`
            );
        }
    } catch (error) {
        core.setFailed(error.message);
    }
}

action();
