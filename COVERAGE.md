# Code Coverage Documentation

## Overview

The ENERY Smart Nutritionist project includes automated tests across the
major layers of the application, including entities, use cases,
interface adapters, data access, and Swing views.

The latest full-project IntelliJ IDEA coverage run produced
approximately:

  Package                 Line Coverage
  --------------------- ---------------
  Overall `enery`               **85%**
  `use_case`                    **96%**
  `interface_adapter`           **93%**
  `view`                        **98%**
  `entity`                      **79%**
  `data_access`                 **62%**
  `app`                          **0%**

The use-case layer exceeds 90% line coverage, and the overall codebase
exceeds 70% line coverage.

## What Is Tested

The automated test suite focuses on meaningful application behavior
rather than testing only trivial getters and setters.

Testing includes:

-   Use-case interactors, including success and failure paths.
-   Controllers and presenters.
-   View models and state objects where they contain meaningful
    behavior.
-   In-memory and file-based data access objects.
-   API data access behavior where external dependencies can be mocked.
-   Entity and strategy behavior.
-   Swing view behavior, including controller delegation, navigation,
    input validation, state updates, field synchronization, and
    user-visible calculations.

## Code Not Fully Unit Tested and Why

### Application Composition (`app`)

The `app` package primarily contains application startup and dependency
wiring. Its purpose is to construct and connect controllers, presenters,
data-access objects, view models, and Swing views.

These classes contain relatively little independent business logic. Much
of their behavior would require integration-style testing of the
complete application rather than isolated unit tests. The individual
components being wired together are tested separately. For this reason,
the `app` package was not a major focus of the unit-test suite.

### External Database and Network Integration

Some data-access implementations communicate with external APIs or
databases. Tests that depend directly on live external infrastructure
can be unreliable because they may depend on API keys, network
availability, service availability, or external data.

Where the design permits dependency injection, external dependencies are
mocked so that parsing, successful responses, failure responses, and
exception handling can be tested deterministically.

Some code that directly depends on real external infrastructure is
therefore treated as integration behavior rather than isolated unit-test
behavior.

### Modal Swing Dialogs

Some Swing error paths display modal dialogs such as
`JOptionPane.showMessageDialog`.

Modal dialogs are not always suitable for automated unit tests because
they can block the test process and require user interaction. Where
appropriate, the surrounding state changes, error handling, controller
behavior, and presenter behavior are tested instead.

### Trivial Accessors and Data Containers

Some trivial getters, setters, constants, and simple data-container
behavior are not individually tested unless they participate in
meaningful application behavior.

The test suite prioritizes business logic, validation, calculations,
state transitions, Clean Architecture interactions, persistence
behavior, and user-visible functionality.

## Running the Tests

From the project root, run:

``` bash
mvn clean test
```

A successful run should finish with `BUILD SUCCESS`.

## Running Coverage in IntelliJ IDEA

To reproduce the full-project coverage report:

1.  Run the tests for the complete `enery` scope with coverage.
2.  Open the IntelliJ IDEA Coverage tool window.
3.  Verify that the report includes the complete package tree,
    including:
    -   `app`
    -   `data_access`
    -   `entity`
    -   `interface_adapter`
    -   `use_case`
    -   `view`
4.  Use the result from this full scope rather than a filtered or
    partial coverage view.

## Coverage Evidence

The current full-project coverage is approximately **85% line
coverage**.

The `use_case` package has approximately **96% line coverage**,
providing strong coverage of the application's use-case logic.

The project therefore exceeds the stated targets of:

-   **more than 90% line coverage for use-case interactor code**, and
-   **more than 70% line coverage for the overall codebase**.

The remaining uncovered code is primarily application wiring, external
integration behavior, trivial code, or UI behavior that is unsuitable or
disproportionately costly to exercise through isolated automated unit
tests.
