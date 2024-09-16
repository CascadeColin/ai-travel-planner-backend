# AI Travel Planner
## Simplifying your vacation planning so you can spend more time vacationing.

### Problem Statement
We all live busy lives and need to take time to relax and recouperate.  However, vacation planning has never been more complex.  It feels like we need a vacation from our vacation planning!  That's where AI Travel Planner comes in.  This software solves the issue of complexity by doing that hard parts for you.  By specifying a ranked list of destinations, preferences for cost, and a list of activites you would enjoy, we can now utilize AI to abstract away the details of finding lodging, flights, and figuring out what to do and see at the destination.

Vacations and travel planning are hard work.  Why go through the effort of spending hours planning it yourself, or spending your hard earned money on a travel agent?  AI puts the power of the entire internet at our finger tips and allows us to automate that entire process.  So lets get started and get your vacation planned today!


### Technical Solution

#### Scenario 1:
Gemma has never traveled to Europe and does not know exactly where to go on the continent.  She also has a busy life and limited time to plan this trip, despite really needing to go on it to get a break from that busy life.  By inputting a list of destination cities, setting a budget, and specifying travel dates, AI Travel Planner does the hard work for her and presents options for all of her destination cities.

#### Scenario 2:
Jeff is a busy executive assistant for the CEO of a Fortune 500 company that needs to quickly plan a large business trip for the company.  He already has a high workload maintaining his boss's daily schedule.  He was able to abstract away planning this business trip by inputting trip details into AI Travel Planner and selecting the "save money" option to help save money on company expenses.

### Glossary - Key Domain Terms (subject to change)
#### Planner
The primary users of this app.  They plan vacations, business travel, weekend getaways, staycations, and all other forms of travel-based leisure.

#### Config
The Planner's settings for the app.  This will hold their UI preference (light/dark mode), their default Preset with customized Options (if applicable), and will store a list of Trips that the user has planned.

#### Options
The settings a Planner toggles or sets that allows for an AI prompt to be created.  There will be presets for business and leisure travel, but the presets can be tweaked.  Later versions may have custom options, but v.1.0 will be limited to features that are known to work fully.

#### Preset
The default presets available in the app.  Initial launch (v.1.0) will contain 2 Presets: business & leisure. Both of these can be customized.

#### Trip
A planned business or leisure travel that is generated via OpenAPI per Options set by a Planner.


### High Level Requirements
* Log in with a valid username and password
* Set user options for Config (optional if already set previously by the Planner)
* Specify a Preset and set Options (travel dates required, rest can be set by Preset)
* Select a Trip based on returned results
* Navigate to external links to complete the booking process (opens monetization options via Travel Partners)
* Browse Trips if the user wants to confirm it was added
* Edit or cancel the Trip if plans change

### User Stories

#### Create Config and set a Preset

Create a profile for the Planner.  This allows for the app to remember if they are a business or leisure traveler.  It also will remember their travel cost preferences.

Precondition:  Planner must have a valid user account.

Post-condition:  Planner will not have to interact with Config and Preset again, unless they choose to change their preferences.

#### Create a Trip

Create a Trip based on Planner's Config and Preset.  The travel options presented to the user can be one or more trips.  The final Trip is chosen by the user and saved in their profile.

Precondition:  Trip must start in the future.  Trip must be on Earth (but I'd love to plan space travel some day!).

Post-condition:  Planner may set the Trip as "fully planned" once they book the flights, lodging, and any other specified details".

#### Edit a Trip

Edit a Trip if plans change.  Any detail of a future Trip can be changed.

Precondition:  Trips end date must be in the future (okay to modify an active trip).

Post-condition:  Trip details are changed and can be displayed in "browse trips".

#### Cancel a Trip

Cancels a Trip if plans change.

Precondition:  Trip start date must be today or a future date.

Post-condition:  Trip is not deleted on the back end.  A parameter hidden from the Planner is set to "cancelled".  That allows the Planner to reinstate the trip at any time (if it meets Precondition).

#### Browse Trips

Browse all Trips, with the option to toggle displaying only past or future trips (default to all trips).  Clicking on a Trip UI card will navigate to a route that renders full details for that trip.  If precondition is met for Edit or Cancel, a button will be shown to quickly navigate there.

Precondition: None

Post-condition:  Easily allows for future trips to be modified or canceled by searching for them in Browse Trips.

