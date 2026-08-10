# ENERY Smart Nutritionist

ENERY Smart Nutritionist is a Java Swing desktop application developed for the
University of Toronto CSC207 course.

The application helps users manage nutrition, exercise, personal goals, meal
logging, recommendations, and progress through a collection of interconnected
use cases implemented using Clean Architecture.

## Features

### User Profile

Users can maintain personal information used by other ENERY features.

Profile information is used to establish health and nutrition goals and can include
information such as physical characteristics, goals, and dietary restrictions.

### Meal and Exercise Tracking

Users can record meals and exercise activities.

The tracking system records information used to calculate nutritional intake and
exercise-related values. Logged information can then be reused by other parts of
the application, including progress tracking.

### Recommendations

ENERY provides meal and exercise recommendations based on information available
about the user.

Meal recommendations can consider nutritional requirements and dietary restrictions,
while exercise recommendations can consider the type, level, or amount of exercise
appropriate to the user's needs.

### Progress Tracking

The Progress feature displays information about the user's progress toward daily
and longer-term goals.

Tracked calorie and nutrient information can be compared with the user's goals,
allowing the application to indicate whether goals have been achieved.

### Search

Users can search for recipes and exercises.

Search functionality provides information that can be used by other features,
including recommendations and logging.

### Health Assessment

The Health Assessment feature evaluates aspects of the user's eating and exercise
patterns and provides an additional way for users to understand their habits.

## Application Workflow

The ENERY features are designed to work together rather than operate as completely
independent tools.

A typical workflow is:

1. The user creates an account or logs in.
2. The user completes their profile and establishes personal goals.
3. The user logs meals and exercise.
4. ENERY uses the available profile and tracking information when producing
   recommendations.
5. The Progress feature summarizes the user's progress toward their goals.
6. The user can continue updating their information as their needs change.

## Architecture

ENERY follows the Clean Architecture principles taught in CSC207.

The application is separated into layers including:

- Entities
- Use Cases
- Interface Adapters
- Data Access
- Views
- Application wiring

A typical use-case flow is:

View
→ Controller
→ Input Boundary
→ Interactor
→ Data Access Interface / Entities
→ Output Boundary
→ Presenter
→ View Model
→ Updated View

This structure follows the Dependency Rule: inner application layers do not depend
directly on Swing views or concrete external data sources.

## Team Development

ENERY was developed as a team project with major functionality divided among team
members.

The original feature division included:

- User Profile
- Tracking and Logging
- Recommendations
- Progress
- Search and Home functionality
- Health Assessment

Although these features were divided among team members, they were designed to
interact through shared application architecture.

For example, profile information can influence recommendations and goals; logging
produces information used by progress tracking; and search functionality provides
recipes and exercises that can be used by other use cases.

The team therefore worked not only on individual features but also on integrating
shared entities, data access, navigation, and application wiring.

## Testing

The project includes automated unit tests for entities, use cases, interface
adapters, and data-access functionality.

To run the test suite with Maven:

```shell
mvn clean test