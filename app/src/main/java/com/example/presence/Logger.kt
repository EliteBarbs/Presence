package com.example.presence

// Logs message using log_textview present in activity_main.xml
class Logger {

    companion object {

        fun log( message : String ) {
            Face.setMessage(  Face.logTextView.text.toString() + "\n" + ">> $message" )
            // To scroll to the last message
            // See this SO answer -> https://stackoverflow.com/a/37806544/10878733
            while ( Face.logTextView.canScrollVertically(1) ) {
                Face.logTextView.scrollBy(0, 10);
            }
        }

    }

}