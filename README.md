##3rd party libraries
1.Picasso for image requests.
2.Jackson for JSON to Model transformation.

#Presenter
Used MVP pattern to create separate requests from activity lifecycle.  It also helps separate business logic from the activity.

#Notes
I decided to have some fun with the task.  There is an "Under 10" slider (Which is more complex than it seems due to peeking and first element alignment) on the "home" page which shows some of the deals under 10 dollars.  The "Under 10" goes away upon search. I included a more dynamic layout which includes different layout styles of deals. These things are intended to keep users interested and engaged. I probably spent more than 4-5 hours...but I was having fun with it.

To better assess a candidate's development skills, we would like to provide the following challenge. We ask that you not spend more than 4-5 hours on this project.

##Submission Instructions

1. First, fork this project on GitHub. You will need to create an account if you don't already have one.
1. Next, complete the project as described below within your fork.
1. Finally, push all of your changes to your fork on GitHub and submit a pull request. You should also email the appropriate address listed in the first section and your recruiter to let them know you have submitted a solution. Make sure to include your GitHub username in your email (so we can match people with pull requests).

## Alternate Submission Instructions (if you don't want to publicize completing the challenge)

1. Clone the repository
1. Next, complete your project as described below within your local repository
1. Email a patch file to ([dev.challenges@livingsocial.com](mailto:dev.challenges@livingsocial.com)) and cc your recruiter.

## Project Description

Build an iOS/Android app that consumes a [json feed](http://sheltered-bastion-2512.herokuapp.com/feed.json). Produce an application that does the following:

1. Provide a list/grid of the summarized content
2. Each row/item should be clickable to some sort of detail view/page/modal for each item. 
3. Provide a mechanism to either filter or search the data that is shown in the list.

Here's what your submission app must include:

1. An app that consumes the above JSON feed and displays the content from the feed in a reasonable manner. It should also gracefully handle failures in the event of an error.
3. An updated README with instructions on setting up your app/any other notes (see below for note about 3rd party libraries)

Your app does not need to:

- Persist any data

Your submission should also include:

- Some sort of tests as part of submission

Your application should be easy to set up and run, and must use Objective-C or Java (No PhoneGap, Xamarin, etc.)
Feel free to use any 3rd party libraries that you want to use. If you decide to use a 3rd party library, please 
include rationale for use in the README.

While we appreciate some iOS developers will prefer Swift, our existing applications are written in Objective-C and we need to understand your facility with that language.

## Evaluation

Evaluation of your submission will be based on the following criteria. Additionally, reviewers will attempt to assess your familiarity with standard libraries. Reviewers will attempt to assess your experience with Android/iOS based on how you've structured your submission.

1. Did your application fulfill the requirements?
1. Did you follow the instructions for submission?
