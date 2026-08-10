# Accessibility Report

## Overview

ENERY Smart Nutritionist is a Java Swing desktop application that helps users
manage nutrition, exercise, personal goals, recommendations, and health-related
information.

Because ENERY contains several connected features developed by different team
members, accessibility was considered both within individual use cases and across
the overall application.

Our main accessibility goals were to provide clear navigation, understandable
feedback, consistent interface behaviour, and to reduce unnecessary user input.

## Clear and Consistent Navigation

ENERY is divided into distinct features, including:

- User Profile
- Meal and Exercise Logging
- Recommendations
- Progress Tracking
- Search
- Health Assessment

The Home page provides an entrance to the application's major features.

We use text labels on buttons and controls rather than relying only on icons.
Related actions are presented using similar interface patterns so that users do
not need to relearn the application when moving between features.

## Preventing Invalid Navigation

Some ENERY features depend on information from other features.

For example, nutrition and exercise functionality may require information from
the user's profile before meaningful calculations can be performed.

Where appropriate, unavailable functionality is disabled until its required
information has been provided. This prevents users from navigating into a feature
that cannot yet operate correctly and provides a clearer application workflow.

## Reducing Repeated Input

The different ENERY use cases share information through the application's domain
and data-access layers.

For example, profile information can be reused by tracking, recommendation, and
progress functionality instead of requiring the user to repeatedly enter the same
information.

This reduces unnecessary interaction and also reduces opportunities for inconsistent
input.

## Input Validation and Error Feedback

ENERY validates user input before performing operations.

When input is invalid or required information is missing, the application displays
feedback explaining what the user needs to correct.

Examples include:

- Missing required fields.
- Invalid numerical input.
- Invalid serving or exercise values.
- Missing profile information required by another feature.

Clear error messages are preferable to allowing an operation to fail without an
explanation.

## Text and Interface Readability

The interface primarily communicates information using text labels.

Important information such as nutrition values, exercise information, progress,
errors, and form labels is displayed as text rather than being communicated only
through colour or imagery.

The application also attempts to maintain consistent layouts and control behaviour
between different Swing views.

## Keyboard Accessibility

ENERY uses standard Java Swing components such as buttons and text fields, which
provide basic keyboard interaction.

However, comprehensive keyboard-only navigation and focus-order testing has not
been completed across every view. We therefore consider this an area for future
accessibility improvement rather than claiming complete keyboard accessibility.

## Accessibility Across Team Features

The original ENERY design divided the application into several connected areas:
Profile, Tracking/Logging, Recommendations, Progress, Search/Home, and Health
Assessment.

These features exchange information. For example:

- Profile information establishes user goals and restrictions.
- Tracking/Logging records meal, nutrient, calorie, and exercise information.
- Recommendations use nutritional requirements and dietary restrictions.
- Progress uses tracked information to display progress toward goals.
- Search provides access to suitable recipes and exercises.
- Health Assessment evaluates aspects of eating and exercise patterns.

Because these features are connected, accessibility and usability decisions in one
part of the application can affect another. During integration, the team therefore
worked to keep navigation, feedback, and interface behaviour understandable across
use cases.

## Teamwork and Integration

The project was developed using Clean Architecture, with functionality separated
across entities, use cases, interface adapters, data access, views, and application
wiring.

Team members developed individual features while sharing common entities,
interfaces, data access objects, and navigation infrastructure.

During integration, the team reviewed and resolved interactions between these
features. This included making sure that shared user information could be reused
between use cases and that changes to shared components did not prevent other
features from operating correctly.

This integration work was important for accessibility because users experience
ENERY as one application rather than as separate individual use cases.

## Current Limitations and Future Improvements

The current application does not provide every possible accessibility feature.

Future improvements could include:

- Comprehensive screen-reader testing.
- Explicit accessible descriptions for all Swing components.
- Full keyboard-only navigation testing.
- Improved and verified keyboard focus order.
- Adjustable font sizes.
- High-contrast display options.
- Additional testing with users who use assistive technologies.

These features should be tested before claiming full support for them.

## Conclusion

ENERY's current accessibility approach focuses on clear navigation, readable text,
consistent interface behaviour, descriptive error feedback, input validation, and
reducing repeated user input.

The interconnected nature of ENERY also means that accessibility is a team concern.
Profile, tracking, recommendations, progress, search, and assessment functionality
must work together to provide users with a coherent experience.