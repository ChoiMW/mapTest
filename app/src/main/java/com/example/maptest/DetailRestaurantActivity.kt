package com.example.maptest

import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.maptest.databinding.ActivityDetailrestaruntBinding
import com.google.android.material.chip.Chip
import me.relex.circleindicator.CircleIndicator3


class DetailRestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailrestaruntBinding
    private lateinit var mPager: ViewPager2
    private lateinit var mIndicator: CircleIndicator3

    private val num_pages=4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailrestaruntBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detailrestarunt)
        setContentView(binding.root)

        /*button for debug*/
        val button_back : Button = findViewById(R.id.button_back)



        /*adding chips*/
        addChips("양식")
        addChips("소개팅")
        addChips("미슐랭2021")
        addChips("분위기좋은")
        addChips("고급스러운")
        addChips("스테이크")
        addChips("파스타")
        addChips("한식")
        addChips("중식")




        //어댑터
        mPager = findViewById(R.id.viewpager)
        val pagerAdapter= MyAdapter(this, num_pages)
        mPager.adapter = pagerAdapter

        //인디케이터
        mIndicator = findViewById(R.id.indicator)
        mIndicator.setViewPager(mPager)
        mIndicator.createIndicators(num_pages, 0)

        // 가로 슬라이드
        mPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL



        /**
         * 이 부분 조정하여 처음 시작하는 이미지 설정.
         * 2000장 생성하였으니 현재위치 1002로 설정하여
         * 좌 우로 슬라이딩 할 수 있게 함. 거의 무한대로
         */
        mPager.currentItem = 0 //시작 지점
        mPager.offscreenPageLimit = 4 //최대 이미지 수
        mPager.registerOnPageChangeCallback( object: ViewPager2.OnPageChangeCallback () {
            override fun onPageScrolled(  position: Int,  positionOffset: Float, positionOffsetPixels:Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    mPager.currentItem = position
                }
            }
            override fun onPageSelected( position :Int) {
                super.onPageSelected(position)
                mIndicator.animatePageSelected(position % num_pages)

            }

        })

        button_back.setOnClickListener {
            finish()
        }

    }

    fun addChips(txt:String)
    {
        binding.chipGroupRestaruantHashtag.addView(
            Chip(this).apply {   text = txt // chip 텍스트 설정
                setChipIconResource(R.drawable.hashtag3)
                setTextAppearance(R.style.chipText)
            }
        )
    }


}