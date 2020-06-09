# Game of Apps Development Intern Interview

Thanks for your interest in the Game of Apps developer intern position! This repo contains a list of tasks for you to do, so we can get a gauge of your skill and passion for development.

You're expected to know the basics of the Git development workflow. If you don't know or you want a review, check out this [tutorial](https://www.raywenderlich.com/179717/open-source-collaboration-using-git-and-github).

## Instructions

The goal is to submit a pull request that accomplishes the following tasks. While the pull requests are public, don't blindly copy others. The follow up interview will involve questions on your submission.

Fork this repository to begin.

## Challenge 1: Algorithms

Your first challenge is to solve two algorithm questions. You'll be judged based on correctness, performance, and code readability.

### Question 1: Find all pairs for a given sum

Create a function that accepts an array of integers and a target sum. The function will print all pairs of integers in the array whose sum is equal to the target sum. Here's an example:

```java
int[] testArray = {2, 4, 5, 1, 3, 5, 4};
int targetSum = 6;
// Expected pairs are (to be printed on the console):
// (2, 4)
// (1, 5)

findPairs(testArray, targetSum);
```

Here's a function signature to start you off:

```java
void findPairs(int[] testArray, int targetSum) {
  // your code here
}
```

### Question 2: Is Palindrome?

Create a function that checks if a string is a palindrome and returns `true` or `false`. A palindrome is a word that reads the same way even if you read it backwards.

Example palindromes:

- radar
- bob
- asdfdsa

Here's a function signature to start you off:

```java
boolean isPalindrome(String testString) {
  // your code here
}
```

## Challenge 2: Android

Your second challenge is to develop a small app from scratch. Here are the requirements:

- It should show a list of images.
- Tapping on an image should navigate to a new screen that shows the selected picture

### Notes:

- A good developer tries to write code that is easy to understand. The industry has a set of best practices that focus on writing easy to maintain code. Here's a set of guidelines on these best practices: [Java Style Guide](https://github.com/raywenderlich/java-style-guide).
