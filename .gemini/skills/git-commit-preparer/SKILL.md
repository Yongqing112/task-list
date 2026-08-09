---
name: git-commit-preparer
description: Prepares, organizes, and structures Git commits by analyzing changes in the workspace. Use when the user wants to commit changes, "wrap up", "prepare commits", or when reviewing git changes for committing. It analyzes git status and diff, recommends whether to split or combine changes, drafts high-quality commit messages, and proposes exact git commands WITHOUT executing them, allowing the user to review, reject, or approve the plan.
---

# Git Commit Preparer

This skill guides the preparation, staging, and styling of Git commits. It performs semantic analysis of file changes, proposes a logical commit strategy (whether to split or combine), and drafts commit commands and messages, but **never executes commits directly**.

## Core Workflow

Always follow these steps when this skill is active:

### 1. Gather Information
Use Git commands to retrieve the complete current state of the workspace:
*   `git status` - check which files are tracked, unstaged, or untracked.
*   `git diff HEAD` - check the actual code changes across all modified files.
*   `git log -n 3` - check recent commit messages to match the repository's writing style (e.g., prefix, casing, verbosity).

### 2. Semantic Change Analysis
Evaluate the gathered differences and categorize changes:
*   **Separation of Concerns**: Keep structural refactoring, bug fixes, feature additions, test updates, and documentation changes in separate commits unless they are tightly bound.
*   **Atomic Commits**: Ensure each commit represents a single logical unit of work.
*   **Determine Commit Count**: Propose splitting into multiple commits if changes cross different layers or feature boundaries. Propose a single combined commit if all changes belong to a single cohesive task.

### 3. Draft Commit Proposals (DO NOT Execute)
Draft a clear plan for the user. For each proposed commit, include:
*   **Commit ID / Sequence**: e.g., "Commit 1 of 2"
*   **Target Files**: List of files to be included.
*   **Reasoning**: Why these changes are grouped together.
*   **Proposed Commit Message**: A highly descriptive message (e.g., following Conventional Commits format `feat: ...`, `refactor: ...`, `test: ...` if used, or matching the style of the local repo's git log). Focus more on *why* than *what*.
*   **Exact Staging and Commit Commands**: List the command sequence (e.g., `git add <file>`, `git commit -m "<message>"`).

### 4. Wait for User Approval
Present the drafted commits to the user in a readable structure and ask for feedback:
*   **DO NOT run any git commit command.**
*   Ask the user to review: *"Please review these proposed commits. If they look good, let me know and I can execute them, or you can run them yourself. If you would like to adjust the plan (e.g., split/combine, edit messages), please let me know."*

### 5. Incorporate Feedback and Adjust
If the user requests changes (e.g., "combine commit 1 and 2", "make the message shorter"):
*   Adjust the commit plan.
*   Present the updated proposal.
*   Wait for approval again.

## Guidelines for Draft Commit Messages

Keep messages clear and communicative:
*   **Header (Subject Line)**: Use a concise summary (typically under 50 characters). Start with an imperative verb (e.g., "Add deadline support", "Refactor task identifier validation") or prefix it if Conventional Commits are preferred (e.g., `feat: ...`).
*   **Body (Optional but recommended for complex changes)**: Use the body to explain *why* the changes were made, the *problem* they solve, and any *non-obvious details*.
*   **Style Match**: Always default to the format observed in the local `git log -n 3` unless instructed otherwise.

## Example Proposal Structure

Present your proposal using the following Markdown format:

### 📋 Proposed Commit Plan

#### 🔹 Commit 1: [Short Commit Description]
*   **Type**: Refactoring / Feature / Test / Docs
*   **Target Files**:
    *   `src/main/.../Task.java`
*   **Reasoning**: Decoupling the Task state update from CLI commands to respect encapsulation.
*   **Draft Message**:
    ```
    refactor: replace direct done setter with complete and reopen methods
    
    Refactors the Task entity to expose expressive domain behaviors instead of 
    a generic setter, preserving encapsulation of internal state.
    ```
*   **Proposed Commands**:
    ```bash
    git add src/main/java/com/codurance/training/tasks/entity/Task.java
    git commit -m "refactor: replace direct done setter with complete and reopen methods"
    ```

---

*Please review the proposed plan. Let me know if you would like me to proceed with execution, or if you want to make any adjustments (e.g., edit messages, split or combine commits).*
