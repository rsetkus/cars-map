 # Cars 
 [![Build Status](https://travis-ci.org/rsetkus/cars-map.svg?branch=master)](https://travis-ci.org/rsetkus/cars-map)
 
 ## Downloads
 Download app from [here](https://github.com/rsetkus/cars-map/raw/master/downloads/app-release.apk)
 ## Design & Dependencies
 ### [Kotlin](https://kotlinlang.org/)
 I :heart: Kotlin. Java is great too but due to certain reasons it has limitations. Let me put it this way, when I am writing Kotlin code most of the time I don't need to think **"how"**, I just need to know **"what"** I have to code.

High-order functions, Sequences, data types you name it. All those language features helps to maintain and scale applications much easier then before. Also and most important thing, Kotlin is first class language by Google.

### [Architecture Components]([https://developer.android.com/topic/libraries/architecture/](https://developer.android.com/topic/libraries/architecture/))

Then it comes question what you should choose when you design application architecture, I think it doesn't really much matter. MVP, MVVM, MVI or any other pattern, they all are very similar and they all do the same thing - separate application concerns.

MVP was the top choice for many years because of strong Android developer community but today I prefer MVVM. It is my first choice because it is created and maintained by Google (they had to step in long time ago!:rage:) and most importantly you don't need to maintain application life cycle which was pain in a neck. Certainly, there are tricks there you can fail, start observing data in wrong place of retained fragment for instance. Anyway, it is great asset for any Android developer.

### [RxJava](https://github.com/ReactiveX/RxJava)

 I was keen to try Kotlin Coroutines because it is native Kotlin way do the complicated threading work. I've tried it before and really enjoyed. However, I feel comfortably with RxJava. Although, I don't consider myself as an expert but I spent great time learning it.

### [Retrofit](https://square.github.io/retrofit/)

No doubt easiest way to setup RESTful  services :ok_hand:. Supports many adapters  for various libraries. :muscle:

### [Gson](https://github.com/google/gson)

Just because easy to setup. There are many JSON serialization/deserialization libraries and which you should use depends on case. Interesting [Reddit thread ](https://www.reddit.com/r/androiddev/comments/684flw/why_use_moshi_over_gson/) to read about Moshi vs Gson.

### [Koin](https://insert-koin.io/)
Dagger2 is for Java projects. Period. I hate when I have to expose inner implementation of Kotlin classes and make them nullable or use *lateinit* just because Dagger cannot inject dependencies. Also, configuration is utterly clumsy. Always, have to go to documentation when I need to change Dagger setup. One of those things which you setup once and don't touch for long time but when you need to change it is hard to understand how it is working. There is one good thing about Dagger, application won't compile if configuration is invalid (code is generated on compile time).

 For Kotlin projects I prefer something which is friendly with Kotlin concepts and implemented with Kotlin. I know Kodein and Koin and my preference of choice is Koin due to simple reasons. Easy to setup, easy to understand (arguably :neutral_face:), lightweight and the way how it injects dependencies to the classes. However, comparing to Dagger it has one drawback - you would get run time exception if Koin setup is invalid. Which you won't if your setup is correct until that point when you explicitly run a code of exact place.

### [Arrow](https://arrow-kt.io/)

Functional Programming is big trend now. Even strictly OOP languages like Java (lambda function implementation) are getting influenced by FP. I am extremely interested into it.

At first, I didn't plan to use it but I came to the point when I couldn't figure out what is best way to deal with multiple data types which represents the state (like view state loading, error, empy etc.). I had few options here:
 
 This one looks fairly simple and should server for all possible execution states. It compiles fine but it is quite obvious there will be some problem. The thing is, on run time you cannot know the type of *T* because it is deleted on compile time. You would need to cast! :fearful:

    sealed class State<out T : Any> {
	    object Loading : State<Nothing>()
	    class Success<out T : Any>(val data: T) : State<T>()
	    class Error<out T : Throwable>(val error: T) : State<T>()
	}


Again another simple solution but not great too. Here we have concrete types and wouldn't need to cast on run time. Problem is that you would need to duplicate it for all data types. So, it is not scalable.

	sealed class State {
	    object Loading : State()
	    class Success(val data: String) : State()
	    class Error(val error: Throwable) : State()
	}
Also, was considering this [implementation](https://ryanharter.com/blog/encapsulating-view-state/). Frankly speaking, it is quite good but I wanted something simpler and at the same time neat.

At the end, I came with an idea that any code execution has only two states/branches - **successful** and **error**. It doesn't have loading, empty or something else. Arrow data types seemed to be a way to go. Also, always wanted to try it :sunglasses:. Arrow library is modular which means you don't need to include everything. Arrow Core module is a good start for programming FP!

### [Picasso](https://github.com/square/picasso)

No particular reason. Just another great library from Square family which easy to pick up and use.

### [MockK](https://mockk.io/)

I am a huge fan of TDD. Mockito for Kotlin projects just feels a bit too Java-ish when you have this elegant Kotlin code (have to escape *when* because in Kotlin when is  keyword). MockK’s main philosophy is offering first-class support for Kotlin features and being able to write idiomatic Kotlin code when using it.

### [Elmyr](https://github.com/xgouchet/Elmyr)

Being a responsible for the code I write I take testing very seriously. I never felt good about hard coding test data and write tests based on explicit data input. Great library when it comes a matter of question how populate test data. Moreover, inspired of this library and influenced by my colleagues/friends, my next focus will be on [Property Based Testing](https://github.com/kotlintest/kotlintest).
