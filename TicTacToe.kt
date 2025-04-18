class Game {
    // Lists the 9 positions on the board. MutableList because the values must change
    // This is similar to Python's [" " for i in range(9)]
    // Index 0-2 is bottom row, 3-5 is middle row, 6-8 is top row
    private val positions = MutableList(9) { " " }

    // Nested list for all possible win combinations. listOf() because the values never change
    // This is used in the checkWin() function and is initialized here
    private val winCombinations = listOf(
        listOf(6, 7, 8), // Top row
        listOf(3, 4, 5), // Middle row
        listOf(0, 1, 2), // Bottom row
        listOf(0, 3, 6), // Left column
        listOf(1, 4, 7), // Middle column
        listOf(2, 5, 8), // Right column
        listOf(0, 4, 8), // Diagonal TL-BR
        listOf(2, 4, 6)  // Diagonal TR-BL
    )

    // Functions initialized with "fun" keyword in Kotlin
    // Main game loop that handles the entire game flow
    fun run() { // Not the fundraiser
        while (true) {
            // Called before each set of moves from the player and bot
            println("Choose an available position by entering 1-9 on the numpad.")
            printBoard()

            // Input Validation Loop - ensures player enters valid position
            // Continues until valid input is received
            while (true) {
                try {
                    val playerInput = readln()                  // "Non-Null Assertive" user input with readln()
                    val inputIndex = playerInput.toInt() - 1    // Set string input to int and -1 for indexing

                    /* Input Validation */
                    // Verify the index is within 0-8
                    if (inputIndex < 0 || inputIndex >= 9) {
                        println("Invalid input.")
                    // Verify empty position by checking if there's an O or X in the given index
                    } else if (positions[inputIndex] == "O" || positions[inputIndex] == "X") {
                        println("That positions is already taken!")
                    // If the input is valid, set position to X.
                    } else {
                        positions[inputIndex] = "X"
                        break
                    }

                // If a non-integer is entered (this would be triggered in line 33 when trying to set to int)
                } catch (e: NumberFormatException) {
                    println("Invalid input. Please enter an integer.")
                }
            }

            // Check for player win. If the function returns true,
            // Then some win condition was met immediately after the player's move
            if (checkForWin()) {
                printBoard()        // Print the final board
                println("You Win!")
                break               // Escape run loop
            }

            // Very *Pythonic* list check for empty positions
            // The player will always make the final move, so we only have to check it here
            if (" " !in positions){
                printBoard()        // Print the final board
                println("Tie!")
                break               // Escape run loop
            }

            // Null-Safe integer initialization
            // This allows the choice to be initialized as null without it exploding
            var botChoice: Int? = null
            while (botChoice == null || positions[botChoice] != " ") { // Bot must pick an empty position
                botChoice = (0..8).random()                     // Standard Library random int generator!!
            }
            positions[botChoice] = "O"

            // Check for bot win the same way as player win
            if (checkForWin()) {
                printBoard()        // Print the final board
                println("You Lose!")
                break               // Escape run loop
            }
        }
    }

    // The classic printBoard
    // Inverted for numpad mapping
    private fun printBoard() {
        println("  ${positions[6]}  |  ${positions[7]}  |  ${positions[8]}")
        println("-----------------")
        println("  ${positions[3]}  |  ${positions[4]}  |  ${positions[5]}")
        println("-----------------")
        println("  ${positions[0]}  |  ${positions[1]}  |  ${positions[2]}")
    }

    // Check for any win when called. Returns true if there exists a win
    private fun checkForWin(): Boolean {
        // For every possible win condition, check if all 3 are the same and not empty
        for (combo in winCombinations) {
            // Check if first 2 match, if second 2 match, and if the first one isn't empty
            if (positions[combo[0]] == positions[combo[1]] && positions[combo[2]] == positions[combo[0]]
                && positions[combo[0]] != " ") {
                return true // Win detected
            }
        }
        return false // No win detected
    }
}


fun main() {
    val game = Game() // Initialize Game class
    game.run()        // Run game
}