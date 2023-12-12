package com.example.quoteapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var sharedViewModel: SharedViewModel
    private var currQuote: Quote = Quote(id = 0, text = "", favorite = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "QuoteDb").build()
                if (appDatabase.quoteDao.getRowCount() == 0) testData()
            }
        }
    }

    fun btnFavoriteQuoteClick(view: View){
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (currQuote.id != 0) {

                    // Remove old favorite quote
                    var oldFavorite: Quote? = appDatabase.quoteDao.getFavoriteQuote()

                    oldFavorite?.let {
                        it.favorite = false
                        appDatabase.quoteDao.upsert(it)
                    }

                    // Update new favorite quote
                    currQuote?.let {
                        it.favorite = true
                        appDatabase.quoteDao.upsert(it)

                        // Update ViewModel
                        sharedViewModel.setFavoriteQuote(it.text)
                    }
                }
            }
        }
    }

    fun btnGenerateQuoteClick(view: View) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val randomQuote = appDatabase.quoteDao.getRandomQuote()
                findViewById<TextView>(R.id.quote).text = randomQuote.text

                currQuote.id = randomQuote.id
                currQuote.text = randomQuote.text
                currQuote.favorite = randomQuote.favorite
            }
        }
    }

    private fun testData() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // Test Data
                val quoteDao = appDatabase.quoteDao
                quoteDao.upsert(Quote(text = "\"When you have a dream, you've got to grab it and never let go.\" — Carol Burnett"))
                quoteDao.upsert(Quote(text = "\"Nothing is impossible. The word itself says 'I'm possible!\" — Audrey Hepburn"))
                quoteDao.upsert(Quote(text = "\"There is nothing impossible to they who will try.\" — Alexander the Great"))
                quoteDao.upsert(Quote(text = "\"The bad news is time flies. The good news is you're the pilot.\" — Michael Altshuler"))
                quoteDao.upsert(Quote(text = "\"Life has got all those twists and turns. You've got to hold on tight and off you go.\" — Nicole Kidman"))
                quoteDao.upsert(Quote(text = "\"Keep your face always toward the sunshine, and shadows will fall behind you.\" — Walt Whitman"))
                quoteDao.upsert(Quote(text = "\"Be courageous. Challenge orthodoxy. Stand up for what you believe in. When you are in your rocking chair talking to your grandchildren many years from now, be sure you have a good story to tell.\" — Amal Clooney"))
                quoteDao.upsert(Quote(text = "\"You make a choice: continue living your life feeling muddled in this abyss of self-misunderstanding, or you find your identity independent of it. You draw your own box.\" — Duchess Meghan"))
                quoteDao.upsert(Quote(text = "\"Success is not final, failure is not fatal: it is the courage to continue that counts.\" — Winston Churchill"))
                quoteDao.upsert(Quote(text = "\"When you have a dream, you've got to grab it and never let go.\" — Oprah Winfrey"))
                quoteDao.upsert(Quote(text = "\"You define your own life. Don't let other people write your script.\" — Carol Burnett"))
                quoteDao.upsert(Quote(text = "\"You are never too old to set another goal or to dream a new dream.\" — Malala Yousafzai"))
                quoteDao.upsert(Quote(text = "\"At the end of the day, whether or not those people are comfortable with how you're living your life doesn't matter. What matters is whether you're comfortable with it.\" — Dr. Phil"))
                quoteDao.upsert(Quote(text = "\"When you have a dream, you've got to grab it and never let go.\" — Carrie Ann Moss"))
                quoteDao.upsert(Quote(text = "\"For me, becoming isn’t about arriving somewhere or achieving a certain aim. I see it instead as forward motion, a means of evolving, a way to reach continuously toward a better self. The journey doesn't end.\" — Michelle Obama"))
                quoteDao.upsert(Quote(text = "\"Spread love everywhere you go.\" — Mother Teresa"))
                quoteDao.upsert(Quote(text = "\"Do not allow people to dim your shine because they are blinded. Tell them to put some sunglasses on.\" — Lady Gaga"))
                quoteDao.upsert(Quote(text = "\"If you make your internal life a priority, then everything else you need on the outside will be given to you and it will be extremely clear what the next step is.\" — Gabrielle Bernstein"))
                quoteDao.upsert(Quote(text = "\"You don't always need a plan. Sometimes you just need to breathe, trust, let go and see what happens.\" — Mandy Hale"))
                quoteDao.upsert(Quote(text = "\"You can be everything. You can be the infinite amount of things that people are.\" — Kesha"))
                quoteDao.upsert(Quote(text = "\"What lies behind you and what lies in front of you, pales in comparison to what lies inside of you.\" — Ralph Waldo Emerson"))
                quoteDao.upsert(Quote(text = "\"I want to be in the arena. I want to be brave with my life. And when we make the choice to dare greatly, we sign up to get our asses kicked. We can choose courage or we can choose comfort, but we can't have both. Not at the same time.\" — Brene Brown"))
                quoteDao.upsert(Quote(text = "\"I'm going to be gone one day, and I have to accept that tomorrow isn't promised. Am I OK with how I’m living today? It's the only thing I can help. If I didn't have another one, what have I done with all my todays? Am I doing a good job?\" — Hayley Williams"))
                quoteDao.upsert(Quote(text = "\"Belief creates the actual fact.\" — William James"))
                quoteDao.upsert(Quote(text = "\"I'm not going to continue knocking that old door that doesn't open for me. I'm going to create my own door and walk through that.\" — James"))
                quoteDao.upsert(Quote(text = "\"It is during our darkest moments that we must focus to see the light.\" — Aristotle"))
                quoteDao.upsert(Quote(text = "\"Not having the best situation, but seeing the best in your situation is the key to happiness.\" — Marie Forleo"))
                quoteDao.upsert(Quote(text = "\"Believe you can and you're halfway there.\" — Theodore Roosevelt"))
                quoteDao.upsert(Quote(text = "\"Weaknesses are just strengths in the wrong environment.\" — Marianne Cantwell"))
                quoteDao.upsert(Quote(text = "\"Just don't give up trying to do what you really want to do. Where there is love and inspiration, I don't think you can go wrong.\" — Ella Fitzgerald"))
                quoteDao.upsert(Quote(text = "\"Silence is the last thing the world will ever hear from me.\" — Marlee Matlin"))
                quoteDao.upsert(Quote(text = "\"In a gentle way, you can shake the world.\" — Mahatma Gandhi"))
                quoteDao.upsert(Quote(text = "\"Learning how to be still, to really be still and let life happen—that stillness becomes a radiance.\" — Morgan Freeman"))
                quoteDao.upsert(Quote(text = "\"Everyone has inside of him a piece of good news. The good news is that you don't know how great you can be! How much you can love! What you can accomplish! And what your potential is!\" — Anne Frank"))
                quoteDao.upsert(Quote(text = "\"All you need is the plan, the road map, and the courage to press on to your destination.\" — Earl Nightingale"))
                quoteDao.upsert(Quote(text = "\"I care about decency and humanity and kindness. Kindness today is an act of rebellion.\" — Pink"))
                quoteDao.upsert(Quote(text = "\"If you have good thoughts they will shine out of your face like sunbeams and you will always look lovely.\" — Roald Dahl"))
                quoteDao.upsert(Quote(text = "\"Try to be a rainbow in someone's cloud.\" — Maya Angelou"))
                quoteDao.upsert(Quote(text = "\"We must let go of the life we have planned, so as to accept the one that is waiting for us.\" — Joseph Campbell"))
                quoteDao.upsert(Quote(text = "\"Find out who you are and be that person. That's what your soul was put on this earth to be. Find that truth, live that truth, and everything else will come.\" — Ellen DeGeneres"))
                quoteDao.upsert(Quote(text = "\"Real change, enduring change, happens one step at a time.\" — Ruth Bader Ginsburg"))
                quoteDao.upsert(Quote(text = "\"Wake up determined, go to bed satisfied.\" — Dwayne \"The Rock\" Johnson"))
                quoteDao.upsert(Quote(text = "\"Nobody built like you, you design yourself.\" — Jay-Z"))
                quoteDao.upsert(Quote(text = "\"I tell myself, 'You've been through so much, you've endured so much, time will allow me to heal, and soon this will be just another memory that made me the strong woman, athlete, and mother I am today.'\" — Serena Williams"))
                quoteDao.upsert(Quote(text = "\"Live your beliefs and you can turn the world around.\" — Henry David Thoreau"))
                quoteDao.upsert(Quote(text = "\"Our lives are stories in which we write, direct and star in the leading role. Some chapters are happy while others bring lessons to learn, but we always have the power to be the heroes of our own adventures.\" — Joelle Speranza"))
                quoteDao.upsert(Quote(text = "\"Life is like riding a bicycle. To keep your balance, you must keep moving.\" — Albert Einstein"))
                quoteDao.upsert(Quote(text = "\"Don't try to lessen yourself for the world; let the world catch up to you.\" — Beyoncé"))
                quoteDao.upsert(Quote(text = "\"Faith is love taking the form of aspiration.\" — William Ellery Channing"))
                quoteDao.upsert(Quote(text = "\"When it comes to luck, you make your own.\" — Bruce Springsteen"))
                quoteDao.upsert(Quote(text = "\"If you don't like the road you're walking, start paving another one!\" — Dolly Parton"))
                quoteDao.upsert(Quote(text = "\"I have learned over the years that when one's mind is made up, this diminishes fear; knowing what must be done does away with fear.\" — Rosa Parks"))
                quoteDao.upsert(Quote(text = "\"The moral of my story is the sun always comes out after the storm. Being optimistic and surrounding yourself with positive loving people is for me, living life on the sunny side of the street.\" — Janice Dean"))
                quoteDao.upsert(Quote(text = "\"We generate fears while we sit. We overcome them by action.\" — Dr. Henry Link"))
                quoteDao.upsert(Quote(text = "\"We are not our best intentions. We are what we do.\" — Amy Dickinson"))
                quoteDao.upsert(Quote(text = "\"I've noticed when I fear something, if I just end up doing it, I'm grateful in the end.\" — Colleen Hoover"))
                quoteDao.upsert(Quote(text = "\"We’ve been making our own opportunities, and as you prove your worth and value to people, they can’t put you in a box. You hustle it into happening, right?\" — Jennifer Lopez"))
                quoteDao.upsert(Quote(text = "\"When you've seen beyond yourself, then you may find, peace of mind is waiting there.\" — George Harrison"))
            }
        }
    }

}