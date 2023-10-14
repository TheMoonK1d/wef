import java.io.File
import java.util.*
import java.security.MessageDigest



fun RandomWordPicker (filePath: String, numberOfWords: Int): List<String> {
    val words = mutableListOf<String>()

    try {
        val wordList = File(filePath).readLines()
        val random = Random()
        while (words.size < numberOfWords) {
            val randomIndex = random.nextInt(wordList.size)
            val randomWord = wordList[randomIndex]

            // Filter words without numbers and with a minimum size of 3 letters
            if (!randomWord.matches(Regex(".*\\d.*")) && randomWord.length >= 3) {
                words.add(randomWord)
            }
        }
    } catch (e: Exception) {
        println("Error reading the file: ${e.message}")
    }

    return words
}

fun hashSeedWords(seedWords: List<String>): String {
    val concatenatedWords = seedWords.joinToString("")
    val sha512 = MessageDigest.getInstance("SHA-512")
    val hashedBytes = sha512.digest(concatenatedWords.toByteArray())

    // Convert the hashed bytes to a hexadecimal string
    val key = hashedBytes.joinToString("") { "%02x".format(it) }

    return key
}


fun main(args: Array<String>) {
    val asciiArt = """  
${'$'}${'$'}\      ${'$'}${'$'}\            ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\  
${'$'}${'$'} | ${'$'}\  ${'$'}${'$'} |          ${'$'}${'$'}  __${'$'}${'$'}\ 
${'$'}${'$'} |${'$'}${'$'}${'$'}\ ${'$'}${'$'} | ${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\  ${'$'}${'$'} /  \__|
${'$'}${'$'} ${'$'}${'$'} ${'$'}${'$'}\${'$'}${'$'} |${'$'}${'$'}  __${'$'}${'$'}\ ${'$'}${'$'}${'$'}${'$'}\     
${'$'}${'$'}${'$'}${'$'}  _${'$'}${'$'}${'$'}${'$'} |${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'} |${'$'}${'$'}  _|    
${'$'}${'$'}${'$'}  / \${'$'}${'$'}${'$'} |${'$'}${'$'}   ____|${'$'}${'$'} |      
${'$'}${'$'}  /   \${'$'}${'$'} |\${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}${'$'}\ ${'$'}${'$'} |      
\__/     \__| \_______|\__|      
    """

    println(asciiArt)

    val filePath = "C:\\Users\\Eyob\\IdeaProjects\\wef\\src\\main\\kotlin\\words.txt"
    if (File(filePath).exists()) {
        val seedWords = RandomWordPicker(filePath, 10)

        if (seedWords.size == 10) {
            println("Here is your seed: ${seedWords.joinToString(", ")}")
            val seedHash = hashSeedWords(seedWords)
            println("Here is your key: $seedHash")
        } else {
            println("Not enough suitable words in the file to generate the seed.")
        }
    } else {
        println("File not found at the specified path: $filePath")
    }
}