package com.example.swapicompose

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        val a:Int = 0/2 //0
//        val b:Int = 1/2 //0
//        val c:Int = 7/2 //3
//        println("$a $b $c")
        //println(listOf(-100, -40, -5, 0, 2, 56).binarySearch(-5))
        val list = twoSum(listOf(3,2, 4).toIntArray(), 6)//1 2
        println("answer ${list[0]} ${list[1]}")
        val list1 = twoSum(listOf(2,7, 11,15).toIntArray(), 9)//0 1
        println("answer ${list1[0]} ${list1[1]}")
        val list2 = twoSum(listOf(3, 2,3).toIntArray(), 6)//0 2
        println("answer ${list2[0]} ${list2[1]}")
        val list3 = twoSum(listOf(2, 5,5,11).toIntArray(), 10)//1 2
        println("answer ${list3[0]} ${list3[1]}")
        //[0,3,-3,4,-1]
        val list4 = twoSum(listOf(0, 3,-3,4,-1).toIntArray(), -1)//0 4
        println("answer ${list4[0]} ${list4[1]}")
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {
        if (nums.size == 2) {
            return listOf(0, 1).toIntArray()
        } else {
            val sortmas: IntArray = nums.clone()
            var firstresult = true
            var tworesult = true
            sortmas.sort()
            val result = listOf(0, 0).toIntArray()
            var nullpoint = 0
            for (i in sortmas.indices) {
                if (sortmas[i] > target / 2) {
                    nullpoint = i
                    break
                }
            }

            //println(nullpoint)
            if(nullpoint>1 && sortmas[nullpoint-1]+sortmas[nullpoint-2]==target){
                for (j in nums.indices) {
                    if (firstresult && nums[j] == sortmas[nullpoint-1]) {
                        result[0] = j
                        firstresult = false
                    }
                    if (tworesult && nums[j] == sortmas[nullpoint-2]) {
                        if (result[0] != j) {
                            result[1] = j
                            tworesult = false
                        }
                    }
                    if (!tworesult && !firstresult)
                        break
                }
            }
            else {
                if (nullpoint == 0) {
                    for (j in nums.indices) {
                        if (firstresult && nums[j] == sortmas[sortmas.size - 1]) {
                            result[0] = j
                            firstresult = false
                        }
                        if (tworesult && nums[j] == sortmas[sortmas.size - 1]) {
                            if (result[0] != j) {
                                result[1] = j
                                tworesult = false
                            }
                        }
                        if (!tworesult && !firstresult)
                            break
                    }
                } else {
                    val masfirst = sortmas.slice(0 until nullpoint)
                    val mastwo = sortmas.slice(nullpoint until sortmas.size)
                    for (i in masfirst.indices) {
                        val check = mastwo.binarySearch(target - masfirst[i])
                        if (check >= 0) {
                            for (j in nums.indices) {
                                if (firstresult && nums[j] == masfirst[i]) {
                                    result[0] = j
                                    firstresult = false
                                }
                                if (tworesult && nums[j] == mastwo[check]) {
                                    if (result[0] != j) {
                                        result[1] = j
                                        tworesult = false
                                    }
                                }
                                if (!tworesult && !firstresult)
                                    break
                            }
                            break
                        }
                    }
                }
            }
            return result
        }

    }
}