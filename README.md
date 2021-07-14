# SAT-sort
Playing around with SAT encodings.

Maven project built on OR-tools, JUnit, and Java 11.

OR-tools set-up : https://developers.google.com/optimization/install

Currently contains a sudoku solver that encodes (OR-tools does the heavy lifting really) a sudoku puzzle on a 2-D array, passes it into a CP-SAT solver, and returns a solved puzzle after decoding the SAT solver's response.
