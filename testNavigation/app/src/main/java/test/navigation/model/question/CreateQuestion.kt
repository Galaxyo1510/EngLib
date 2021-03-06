package test.navigation.model.question

import test.navigation.model.dict.ParseWordList
import test.navigation.model.dict.Word
import test.navigation.store.Account

object CreateQuestion {
    fun from(word: Word): Question{
        val options = getOptions(word)
        val correctAnswer = options.indexOf(word.word) + 1
        return Question(0,
                word.meanings[0].definitions[0].definition,
                options[0],
                options[1],
                options[2],
                options[3],
                correctAnswer)
    }

    private fun getOptions (word: Word): List<String>{
        val listRef = createListReference(word)
        val options = ArrayList<String>()
        for (i in 0..3){
            options.add("option"+ (i+1))
        }
        val correctPos = (0..3).random()
        options[correctPos] = word.word
        for (i in 0..3){
            if (i != correctPos){
                val selection = listRef.random()
                listRef.remove(selection)
                options[i] = selection
            }
        }
        return options
    }
    private fun createListReference(word: Word): ArrayList<String>{
        var ref = ParseWordList.from(Account.REF_WORD_LIST) as ArrayList
        for (x in word.meanings)
            for (y in x.definitions)
                if (y.synonyms != null){
                    for (z in y.synonyms)
                        if(Account.REF_WORD_LIST.indexOf(z) != -1) ref.remove(z)
                }
        if(Account.REF_WORD_LIST.indexOf(word.word) != -1) ref.remove(word.word)
        return ref
    }
}