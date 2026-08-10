# Accessibility Report

## Overview

ENERY Smart Nutritionist is a Java Swing desktop application that helps users manage nutrition, exercise, personal goals, recommendations, and health-related information. Because ENERY contains several connected features, accessibility is considered both within individual use cases and across the overall application.

The current application focuses on clear navigation, understandable feedback, consistent interface behaviour, readable text, input validation, and reducing unnecessary repeated input. The following sections discuss ENERY using the seven Principles of Universal Design.

## 1. Equitable Use

ENERY presents its major functionality through the same Java Swing interface rather than creating separate workflows for different categories of users. Important information such as nutrition values, exercise information, progress, errors, and form labels is communicated primarily through text rather than relying only on colour or imagery.

The current version has not undergone comprehensive screen-reader or assistive-technology testing. Future development could improve equitable use by adding and verifying accessible descriptions, screen-reader compatibility, adjustable font sizes, and high-contrast display options.

## 2. Flexibility in Use

ENERY provides several connected ways for users to manage their wellness information. Users can maintain a profile, log meals and exercise, search for information, receive recommendations, view progress, and use the Health Assessment feature.

Users can also update their information as their goals or circumstances change. Future improvements could provide additional flexibility through more complete keyboard-only navigation, configurable interface options, and additional ways to enter or review information.

## 3. Simple and Intuitive Use

ENERY is divided into distinct features such as User Profile, Meal and Exercise Logging, Recommendations, Progress Tracking, Search, and Health Assessment. The Home page provides an entrance to the application's major features, and text labels are used on buttons and controls rather than relying only on icons.

Related actions use similar interface patterns so that users do not need to relearn the application when moving between features. Features also share information through the application's architecture, allowing profile and tracking information to be reused by other use cases.

## 4. Perceptible Information

The interface primarily communicates information through visible text labels. Nutrition values, exercise information, progress, form labels, and error messages are presented directly to the user instead of being communicated only through colour or imagery.

The application also attempts to maintain consistent layouts and control behaviour across Swing views. However, comprehensive screen-reader testing has not yet been completed, so stronger support for non-visual presentation of information is an important area for future improvement.

## 5. Tolerance for Error

ENERY validates user input before performing operations. When input is invalid or required information is missing, the application provides feedback explaining what the user needs to correct, including cases such as missing required fields, invalid numerical input, invalid serving or exercise values, and missing profile information.

Some features also depend on information from other features. Where appropriate, unavailable functionality can be prevented until required information has been provided, reducing the chance that users enter a workflow that cannot operate correctly.

## 6. Low Physical Effort

ENERY reduces repeated input by allowing information to be shared between connected use cases. For example, profile information can be reused by tracking, recommendations, and progress instead of requiring the user to repeatedly enter the same information.

The application uses standard Swing controls such as buttons, text fields, check boxes, and combo boxes. These controls provide basic keyboard interaction, although complete keyboard-only navigation and focus-order testing remain areas for future improvement.

## 7. Size and Space for Approach and Use

As a desktop Java Swing application, ENERY does not involve physical approach or reach in the same way as a physical product. The relevant concern is whether interface controls and information can be comfortably located and operated on the screen.

The current application uses conventional Swing layouts and labelled controls, but it has not been comprehensively tested across different screen sizes, display scaling settings, or accessibility configurations. Future improvements could include adjustable font sizes, scalable interface elements, improved spacing, and testing at different display resolutions.

## Target Market

If ENERY were distributed commercially, it would primarily be marketed toward people who want a convenient way to manage nutrition and exercise information in one application. Potential users could include university students, working adults, and people beginning to develop healthier eating or exercise habits. The application may be particularly useful to users who want to track meals and exercise while receiving recommendations based on their personal goals and dietary restrictions. ENERY would be positioned as a general wellness, organization, and tracking tool rather than a replacement for professional medical or nutritional advice.

## Demographic and Ethical Considerations

ENERY may be less accessible or less likely to be used by certain demographics. The current desktop application requires access to a compatible computer and assumes some familiarity with nutrition, exercise, and computer interfaces, which may create barriers for users with limited access to technology or limited digital literacy. Users with visual or motor disabilities may encounter additional barriers because comprehensive screen-reader, keyboard-only, focus-order, and assistive-technology testing has not yet been completed. Language may also create a barrier because the current interface is designed primarily for English-speaking users, and external food or exercise data may not represent all cultural diets and activities equally well. Future development should therefore consider localization, broader cultural representation in data sources, and testing with users from different backgrounds and with different accessibility needs.

## Accessibility Across Team Features

The ENERY design divides the application into several connected areas: Profile, Tracking and Logging, Recommendations, Progress, Search and Home functionality, and Health Assessment.

These features exchange information. Profile information establishes user goals and restrictions; Tracking and Logging records meal, nutrient, calorie, and exercise information; Recommendations use nutritional requirements and dietary restrictions; Progress uses tracked information to display progress toward goals; Search provides access to recipes and exercises; and Health Assessment evaluates aspects of eating and exercise patterns.

Because these features are connected, accessibility and usability decisions in one part of the application can affect another. During integration, the team therefore worked to keep navigation, feedback, and interface behaviour understandable across use cases.

## Current Limitations and Future Improvements

The current application does not provide every possible accessibility feature. Future improvements could include:

- Comprehensive screen-reader testing.
- Explicit accessible descriptions for Swing components.
- Full keyboard-only navigation testing.
- Improved and verified keyboard focus order.
- Adjustable font sizes.
- High-contrast display options.
- Testing at different display sizes and scaling settings.
- Localization and support for additional languages.
- Additional testing with users who use assistive technologies.

These features should be implemented and tested before claiming complete support for them.

## Conclusion

ENERY's current accessibility approach focuses on clear navigation, readable text, consistent interface behaviour, descriptive error feedback, input validation, and reducing repeated user input. The seven Principles of Universal Design also identify areas where the application can be improved, particularly assistive-technology support, interface customization, keyboard navigation, and adaptability to different users and display environments.

Because ENERY's profile, tracking, recommendations, progress, search, and assessment functionality work together, accessibility should continue to be treated as an application-wide and team-wide concern.
