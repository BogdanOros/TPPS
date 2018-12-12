package io.boros

import io.boros.CoffeeShop
import spock.lang.Specification
import spock.lang.Unroll

class CoffeeShopSpec extends Specification {

    @Unroll
    def "should validate ctor parameters (#x, #y)"() {
        when:
        new CoffeeShop(x, y)
        then:
        thrown(IllegalArgumentException)
        where:
        x    | y
        1    | 0
        0    | 1
        0    | 0
        1001 | 0
        1001 | 10001
    }

    @Unroll
    def 'should create CoffeeShop(#x, #y)'() {
        when:
        def shop = new CoffeeShop(x, y)
        then:
        shop != null
        where:
        x    | y
        1    | 1
        1    | 1000
        1000 | 1
        1000 | 1000
    }

    def 'should return decremented x from getter'() {
        given:
        def shop = new CoffeeShop(10, 10)
        expect:
        shop.x == 9
    }


    def 'should return decremented y from getter'() {
        given:
        def shop = new CoffeeShop(10, 10)
        expect:
        shop.y == 9
    }

}