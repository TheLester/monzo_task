# Monzo Code Test for Android Engineers

<img src="screenshoot2.png" width="100%" />
<img src="screenshoot1.png" width="100%" />

## Completed task of Monzo Code Test for Android Engineers

I've spent a little bit more then 4 hours - just wanted to create finished task.
Answers for instructions questions:

#what were your priorities, and why?

My main priority was fixing current UI issues and adding new according to sketch file.
While concentrating on that, I noticed that initially, the project had Kotlin and Java files.
I decided to convert all files to Kotlin, integrate epoxy library to simplify displaying and updating items and recyclerview
(https://github.com/airbnb/epoxy)

I think UI/UX is very important and that's why it was my main priority.

#if you had another two days, what would you have tackled next?

I would :
-try to optimize issue on main screen (currently it has a little freezing on list screen start).
-implement local store with Room or Realm and implement unit/UI tests.
-changing architecture from MVP to MVVM (with architecture components from JetPack) would be also one of goals.
-global error handling
-if it's needed - handle screen rotation.

#what would you change about the visual design of the app?

It's a hard question, because app is a demo and has only 2 screens.
For detail screen - I would change toolbar color,
because white back button and favorite icon can be invisible if content picture is white.
Maybe transition animation of picture and title is good solution here.

As for main screen with articles list - I wouldn't use cards to display a group of articles.
But that requires a discussion with a designer or UX expert.