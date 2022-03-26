package com.example.maptest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.Exception

class MyAdapter(drActivity: DetailRestaurantActivity, nPages: Int) : FragmentStateAdapter(drActivity) {

    var cnt: Int = nPages

    override fun createFragment(position:Int): Fragment {
        var idx : Int = getRealPosition(position)

        if(idx==0){
            return  DetailRestaruntImageFragment1()
        }
        else if(idx==1) {
            return  DetailRestaruntImageFragment2()

        }
        else if(idx==2){
            return  DetailRestaruntImageFragment3()
        }
        else {
            // 더보기 누르는 경우 사진 액티비티 실행
            return  DetailRestaruntImageFragment4()
        }
    }


    override fun getItemCount() :Int {
        return 2000
    }


    fun getRealPosition(position :Int) : Int {
        return position % cnt
    }


}
