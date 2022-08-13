# belt-tools

There are 2 apps in this repository, an old version and a new version.
The purpose of this is to show some of my improvement at the time of making the new app.
The newer version is simple, and meant to keep the theme of the original.

This was the first real app I made.
I used it to help me in my hardware job.

The newer version is built in MVVM architecture and a local database.
The old version uses shared preferences to store data and almost all the code is in the activities.

Old app images are to the left, new app images to the right

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

<p align="left" style="display:flex">
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184462871-fb917450-cda1-434b-91fe-9167ed9dede2.jpg" />
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184462872-3f91569b-29ac-4021-81bc-bbbc218e0afe.jpg" />
  -
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184464648-1a466e59-6a33-4452-90d2-b2484b79cd93.gif" />
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184464650-250d34a6-09d6-4055-8338-5b0d3ad5c0da.gif" />
</p>

- The Funcitons in the main screen are used to calculate things like materials needed for a given space or number of louvers for a blind
- In SKUs and in Pallets, users can can add SKU numbers to work on (and a note) to a recyclerview to keep track of them

- The original version has a More Options screen instead of a navigation drawer

---

### Orders

<p align="left" style="display:flex">
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184464599-3fe4c587-3a50-43ff-b0af-856d8e637b2d.gif" />
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/183625899-c0406d44-e837-4db9-9124-a0e51310eb50.gif" />
</p>

- Can create, update, read, or delete orders from the Room database
- Click on an order item in the recyclerview to display more information about an order
  - Orders have an order numner, contents, and a note
- Can also look up an order by typing it's order number

- In the original version, storing orders is done using shared preferences
  - [here](https://github.com/edcres/belt-tools/blob/master/old-belt-tools/app/src/main/java/com/aldreduser/belttools/DeptExtensionsActivity.kt) is the source code for the original version

---

### Extensions and Notes

<p align="left" style="display:flex">
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184464619-e9508eb8-954f-4680-b28c-639ee13d8b46.gif" />
  <img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/184464627-11eb1fd6-18fd-46e2-af01-f19a62dca3d9.gif" />
</p>

- In the Extensions screen, users can write the phone numbers for different departments in a Room database
- In the Notes screen, users can pick a department and write down notes for it
