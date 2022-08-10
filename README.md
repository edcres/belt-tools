# belt-tools

There are 2 apps in this repository, an old version and a new version
The purpose of this is to show some of my improvement at the time of making the new app
The newer version is simple, and meant to keep the theme of the original

This was the first real app I made
I used it to help me in my hardware job

The newer version is built in MVVM architecture and a local database
The old version uses shared preferences to store data and almost all the code is in the activities

---

### Tools and skills used:

- MVVM architecture
  - Shared ViewModel
- Material Design
- Navigation drawer
- Jetpack Navigation Component
- SQLite Room local storage
- LiveData
  - Livedata Observers
  - Kotlin Flow
- Kotlin coroutines (for synchronous excecutions)
- EditText values stored in Room immediately as user types
- RecyclerView
- Recursive function to turn a decimal to a fraction

---

### Functions, SKUs, and Pallets

- The Funcitons in the main screen are used to calculate things like materials needed for a given space or number of louvers for a blind
- In SKUs and in Pallets, users can can add SKU numbers to work on (and a note) to a recyclerview to keep track of them

- The original version has a More Options screen instead of a navigation drawer

---

### Orders

- Can create, update, read, or delete orders from the Room database
- Click on an order item in the recyclerview to display more information about an order
  - Orders have an order numner, contents, and a note
- Can also look up an order by typing it's order number

- In the original version, storing orders is done using shared preferences
  - [here](https://github.com/edcres/belt-tools/blob/master/old-belt-tools/app/src/main/java/com/aldreduser/belttools/DeptExtensionsActivity.kt) is the source code for the original version

---

### Extensions and Notes

- In the Extensions screen, users can write the phone numbers for different departments in a Room database
- In the Notes screen, users can pick a department and write down notes for it
