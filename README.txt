=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 11361639
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: board of letters and colors much more efficient as 2D array

  2. Collections: collection of words, appropriate since list of words need to have functions like search,
   and get and hvaing indexes with map makes getting random word much easier

  3. File I/O: need to import massive lists of words,
  also need to read and write to saved game file

  4. JUnit Testable Component: need to be able to test smaller functions like the many
  in wordle model like checkvalid, reset, and more

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
    GameBoard is the GUI, RunWordle is the large scale build of the wordle game,
    wordle is the model behind everything


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  load was quite difficult to implement since it required filewriters and readers which are difficult to
  work with sometimes when it comes to the little details


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I think private state is encapsulated decently well, everything is in the model and I have getters and setters for
  the variables that need them when board needs to access them. I don't think there's
  necessarily anything major I would refactor.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
  used a list of wordle words
