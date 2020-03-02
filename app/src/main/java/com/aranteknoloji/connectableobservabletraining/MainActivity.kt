package com.aranteknoloji.connectableobservabletraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var counter = 0
    private val maps = listOf("First Item", "Second Item", "Third Item",
        "Forth Item", "Fifth Item", "Sixth Item", "Seventh Item", "Eighth Item", "Nine Item")
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memoryObservable = getDbObservable().replay(1).refCount()
            .doOnSubscribe { Log.i("MyTag", "memoryObservable SUBSCRIBE MEMORY") }
            .doOnCancel { Log.i("MyTag", "memoryObservable UN-SUBSCRIBE MEMORY") }
            .doOnEach { Log.i("MyTag", "memoryObservable EACH MEMORY => ${it.value}") }

        btn_emitter.setOnClickListener {
            myDatabase(this).myDao().insertData(MyEntity(0, maps[counter++ % maps.size]))
                .subscribeOn(Schedulers.io())
                .subscribe()
        }

        btn_unsubscribe.setOnClickListener {
            disposables.clear()
        }

        //First Subscriber
        disposables.add(
            memoryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
                txt_sub1.text = it.data
            }
        )

        //Second Subscriber
        disposables.add(
            memoryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
                txt_sub2.text = it.data
            }
        )

        //Third Subscriber
        disposables.add(
            memoryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
                txt_sub3.text = it.data
            }
        )


        //Additional Subscribers
        //Forth Subscriber
        btn_sub4.setOnClickListener {
            disposables.add(
                memoryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
                    txt_sub4.text = it.data
                }
            )
        }

        //Fifth Subscriber
        btn_sub5.setOnClickListener {
            disposables.add(
                memoryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
                    txt_sub5.text = it.data
                }
            )
        }

        //Sixth Subscriber
        btn_sub6.setOnClickListener {
            disposables.add(
                memoryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
                    txt_sub6.text = it.data
                }
            )
        }
    }

    private fun getDbObservable() =
        myDatabase(this).myDao().getDataObservable(0)
            .doOnSubscribe { Log.i("MyTag", "getDbObservable SUBSCRIBE DB") }
            .doOnCancel { Log.i("MyTag", "getDbObservable UN-SUBSCRIBE DB") }
            .distinctUntilChanged()
            .doOnEach { Log.i("MyTag", "getDbObservable EACH DB => ${it.value}") }
}
