# Key Terms
* Planner:  a user of this service.  They plan and go on Trips.
* Preset:  default preferences for a Planner.  Initial launch will have a business and leisure travel preset.
* Config:  optional preferences within a Preset.
    - NOTE: Storing Presets and Config separately to keep them loosely coupled.  I want to maintain the ability to expand on config settings later and add presets, so prefer not to lock them together as there may be additional parameters needed in the future that would be difficult to untangle with a tightly coupled Preset and Config.

# Dev Philosophy
I have never attempted a project of this scale in this short of a time frame.  Therefore, I am not planning this to the nitty gritty details of assigning a task to each class and method.  I simply don't have a full grasp of everything needed, and also do not have time to plan in that much detail.  To compensate, I am being conservative on time estimates knowing I will run into unforseen technical issues.  I have also alloted a large amount of time to R&D for brushing up on TypeScript and fully understanding OpenAPI.  If I end up ahead of this conservative schedule, I will implement stretch goals.

# Create Config and set a Preset - Initial task after R&D

Create a profile for the Planner.  This allows for the app to remember if they are a business or leisure traveler.  It also will remember their travel cost preferences.

Precondition:  Planner must have a valid user account.

Post-condition:  Planner will not have to interact with Config and Preset again, unless they choose to change their preferences.

## Technical details:  

* SQL:  Relies on Planner (user), Config, and Preset database tables.  A Preset will be assigned to Planner as a foreign key based on user choice.  Config will be determined by the user and assigned as a foreign key (no custom options for initial launch).  Time taken: 2 hour for writing DML and DDL for prod and test DBs (including test data).

* Data layer:  getPlannerByID (find the user), getPresets (to display in UI for Planner to choose), getConfig (probably done within the getPreset call to fetch the Preset's default Config), setPreset (sets Planner's Preset in Planner object), setConfig (sets Planner's Config in Planner object).  Time taken: 2 hour

* Data layer tests:  Assure Spring Boot is modifying Planner table with Preset and Config settings, assure get requests for Planner, Config, and Preset work, assure Planner has a Preset and Config, and if not prompt UI layer to route to set it up (should only occur on Planner's first login) with a `settingsSet: false` tag.  Time taken: 1 hour 

* Models:  Uses Planner, Preset, and Config.  They are POJOs so the only validation needed are null checks for specific settings to assure the UI is not sending us bad data.  Time taken: 2 hour

* Domain layer:  Simple null checks.  Check that the Preset and Planner are not null when UI requests it via GET request.  Check that set Preset and Config for the Planner does not contain null parameters when POST request comes in from UI. Time taken: 2 hour

* Domain tests:  Use mocks to check for null values.  Use negative test cases to test error messages if null values are detected.  Use positive tests to assure there are no error messages when everything works as intended.  Time taken: 2 hour

* Controllers:  Uses a controller for Planner, Preset, and Config.  GET and PUT is needed for Planner (fetch the Planner on login, PUT to modify their Preset and Config settings).  GET is required for Preset (so the UI can present options to Planner).  GET is required for Config (to get default settings for each Preset).  Note, PUT for Planner will store the Preset and Config details in a SQL table attached to the Planner. Time taken: 2 hour

* Spring Boot:  Will need annotations for Controllers and their methods (@Bean).  All Controllers will route to a single Main method, so that is a one and done (but still a task, albeit a simple one).  Models will need annotations as well.  Time taken: 1 hour

* React:  Components needed are as follows:  Login, Auth, SetPreferences (rendered for both of initial account setup and editing preferences), App (for setting up routing).  Style using shadcn.   Time taken: 2 hours

* Total time for these tasks:  16 hours

# Create a Trip - Done after 'Create Config and set a Preset'

Create a Trip based on Planner's Config and Preset.  The travel options presented to the user can be one or more trips.  The final Trip is chosen by the user and saved in their profile.

Precondition:  Trip must start in the future.  Trip must be on Earth (but I'd love to plan space travel some day!).

Post-condition:  Planner may set the Trip as "fully planned" once they book the flights, lodging, and any other specified details".

## Technical details:  

* SQL:  Uses Planner table (to assign plannerId as a FK to Trip && get preset/config), Trip table (to store the created Trip, edit the trip, set cancelled flag, set fully planned flag).  Time taken: 2 hours.

* Data layer:  GET Planner's Config and Preset settings, POST a Trip.  Time taken: 2 hours.

* Data layer tests:  Confirm that Trips can be created in Trip table.  Confirm that error messages work with negative tests and with global error handler. Time taken: 2 hours.

* Models:  Planner, Trip.  Need to validate that Trip contains all non-nullable fields.  Need to validate that Planner is not null (use same methods as creating config/preset).  Time taken: 2 hours.

* Domain layer:  Validate that a Trip contains required parameters before creating in DB.  These include travel dates, location data, lodging, flights, and lists for activities.  Time taken: 2 hours

* Domain tests:  Fully test Trip validation with positive and negative tests.  Time taken: 2 hours

* Controllers:  POST a Trip, GET a Planner (with attached Preset and Config).  Time taken: 2 hours

* Spring Boot:  Will need annotations for Controllers and their methods (@Bean).  All Controllers will route to a single Main method, so that is a one and done (but still a task, albeit a simple one).  Models will need annotations as well.  Time taken: 1 hour

* React:  TripDetails (where the user will specify the broad details of the trip), ViewIdeas (fetches an OpenAPI request based on CreateTripDetails and displays a list of options), ConfirmSelection (prompts the user with Trip details and asks to confirm trip selection).  Time taken: 4 hours

* Total time for these tasks:  19 hours

# Edit a Trip - Done after 'Create a Trip'


Edit a Trip if plans change.  Any detail of a future Trip can be changed.

Precondition:  Trips end date must be in the future (okay to modify an active trip).

Post-condition:  Trip details are changed and can be displayed in "browse trips".

## Technical details:  

* SQL:  Uses Trip table and Planner table.  Schema will already be fully built from creating a trip.  Time Taken: 1 hour

* Data layer:  PUT an existing trip and modify SQL row in Trip table.  Time taken: 2 hours.

* Data layer tests:  Positive and negative tests for modifying Trip table.  Modify global error handler as exceptions are encountered.  Time taken: 2 hours.

* Models:  Uses Trip model only (Planner just contains a FK with tripId, so will remain the same).  Use the validation methods already written for creating a Trip to validate that edits to the Trip are okay.  Time taken: 2 hours.

* Domain layer:  Ideally, this will use the same validation as creating a trip.  May have to implement an enum for validation flags (CREATE, UPDATE, DELETE) if it runs into hiccups.  Time taken: 2 hours.

* Domain tests:  Fully test Trip validation with positive and negative tests.  Time taken: 2 hours

* Controllers:  PUT a Trip, GET a Planner (to access their trips).  Time taken: 2 hours.

* Spring Boot:  Will need annotations for Controllers and their methods (@Bean).  All Controllers will route to a single Main method, so that is a one and done (but still a task, albeit a simple one).  Models will need annotations as well.  Time taken: 1 hour

* React:  TripDetails, but utilizing `useParams` hook to get tripId to populate the view with existing data.  ConfirmSelection for prompting user confirmation of Trip modification (using an edit flag of some kind to render the text appropriately).  Time taken: 4 hours.

* Total time for these tasks:  18 hours.

# Cancel a Trip - Done after 'Edit a Trip'

Cancels a Trip if plans change.

Precondition:  Trip start date must be today or a future date.

Post-condition:  Trip is not deleted on the back end.  A parameter hidden from the Planner is set to "cancelled".  That allows the Planner to reinstate the trip at any time (if it meets Precondition).

## Technical details:  

* SQL:  Modifies Trip based on tripId to set `cancelled BIT = 1`.  This will maintain the data while rendering it hidden to the user, unless they wish to reinstate it.  Time taken: 1 hour.

* Data layer:  editTripStatus to change the boolean flag `cancelled` for a Trip.  Time taken: 1 hour.  

* Data layer tests:  Verify that the cancelled boolean/bit can be modified for a Trip with positive and negative tests.  Time taken: 1 hour.

* Models:  Uses Trip model to maintain data integrity.

* Domain layer:  Validate that the incoming request is from a valid user and that the Trip is not null.  Create Result returns with error messages if problems arise.  Time taken: 1 hour.

* Domain tests:  Positive tests for successful cancelations and test error messages for negative results.  Time taken: 1 hour.

* Controllers:  GET a trip (for fetching the trip to cancel), PUT a Trip (to set `cancelled = true`).  There are no DELETE requests for a Trip, we want maximum user comfort and to account for people flip-flopping on their decisions.  Time taken: 1 hour.

* Spring Boot:  Will need annotations for Controllers and their methods (@Bean).  All Controllers will route to a single Main method, so that is a one and done (but still a task, albeit a simple one).  Models will need annotations as well.  Time taken: 1 hour

* React:  ConfirmSelection component, but rendering it with conditional text for the cancellation context.  ViewCanceledTrips to render Trips with boolean/bit flag `cancelled = true`.  ReinstateConfirmation to confirm that a Planner wants to reinstate a previously canceled Trip.  Time Taken: 2 hours.

* Total time for these tasks:  9 hours.

# Browse Trips - Done after 'Cancel a Trip' 

Browse all Trips, with the option to toggle displaying only past or future trips (default to all trips).  Clicking on a Trip UI card will navigate to a route that renders full details for that trip.  If precondition is met for Edit or Cancel, a button will be shown to quickly navigate there.

Precondition: None

Post-condition:  Easily allows for future trips to be modified or canceled by searching for them in Browse Trips.

## Technical details:  

* SQL:  Planner and Trip table are used.  Need to be able to view all Trips associated with the logged in Planner.  Time taken: 1 hours.

* Data layer:  GET Planner (to access their associated Trips).  GET list of Trips (to get the Planner's trips).  Time taken: 1 hours.

* Data layer tests:  Assure that GET requests function properly with positive and negative tests. GET Planner (to access their associated Trips).  Time taken: 1 hours.

* Models:  Planner, Trip.

* Domain layer:  Verify that Planner is not null (should be using plannerId associated with currently logged in user).  Verify that Trip data is not null.  A Planner can have zero Trips, so elegantly handle that with a result message passed up to the UI.  Time taken: 1 hour.

* Domain tests:  Validate null checks.  Verify that a Planner with no Trips is handled gracefully.  Verify that a Planner with Trips is properly populating Trip data.  Positive and negative tests for each.  Time taken: 1 hour.

* Controllers:  GET Trips by plannerId.  Time taken: 1 hour.

* Spring Boot:  Will need annotations for Controllers and their methods (@Bean).  All Controllers will route to a single Main method, so that is a one and done (but still a task, albeit a simple one).  Models will need annotations as well.  Time taken: 1 hour.

* React:  ViewTrips (renders a card or list for each trip, or if no Trips are present a NavLink that routes to TripDetails so they can plan their first Trip).  Routes to ViewTrip to view a specific Trip (and modify/cancel it if preconditions are met).  Time taken: 2 hours.

* Total time for these tasks:  9 hours.

# R&D for OpenAPI and TypeScript - to be finished by end of day 9/16

## OpenAPI
* Will need to have a general understanding of the abilities of the API and understand the datatype it will provide (JSON, XML, something else?).
* Will need to at least know how to properly search/browse API documentation to troubleshoot issues and learn syntax.
* Will need to create test data based to mimic API calls to limit development cost (given that this is not a free API).
* Time taken: 1 hour.

## TypeScript
* Will need to generally understand how to utilize TypeScript docs (review as I have used it in the past).
* Will need to review creating interfaces, tuples, and data structures.
* Time taken: 1 hours.

# Final Prep and Presentation - Done after all technical tasks completed
* Create a 4-6 slide presentation describing myself, how I found programming, and my project.  Time taken: 1 hour.
* Diagrams for SQL and Java classes.  Wireframes for react components.  Time taken: 1 hour.
* Review requirements and assure nothing was missed.  Allocate time to correcting mistakes.  Time taken: 2 hours.


# Summary

Currently, the total time estimated is: 77 hours.  This includes working through Saturday and Sunday as half days.  Given that Monday 9/16 has turned into a planning day, that leaves me with 3 hours to spare as "wiggle room" in addition to the conservative estimates I have baked into the first few tasks.  That also assumes I will only work 8 hours during business days, but I have gone well beyond that in past jobs when necessary.

If I complete this faster, there are some ideas I have to expand on the project:
* Implement group Trips that has a specified Planner as an admin for the trip.
* Implement creating a new Planner (current project will have pre-made user logins).