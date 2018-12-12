package io.boros

import spock.lang.Specification
import spock.lang.Unroll

class CoffeeDistanceCalcTest extends Specification {

    @Unroll
    def "validate ctor CoffeeDistanceCtor(#x, #y)"() {
        when:
        new CoffeeDistanceCalc(new CoffeeShop[1], x, y)
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

    def "should return matrix max point"() {
        given:
        def shop = new CoffeeDistanceCalc(new CoffeeShop[1], 3, 3)
        int[][] m = [[1, 2, 3], [2, 3, 4], [5, 4, 3]]
        when:
        CoffeeDistanceCalc.CoffeePoint point = shop.maxPoint(m)
        then:
        point.x == 3
        point.y == 1
        point.count == 5
    }

    @Unroll
    def 'should calculate correct point{#x, #y} for distance #dist'() {
        given:
        CoffeeShop[] shops = [
                new CoffeeShop(1, 1),
                new CoffeeShop(1, 2),
                new CoffeeShop(3, 3),
                new CoffeeShop(4, 4),
                new CoffeeShop(2, 4)
        ]
        def calc = new CoffeeDistanceCalc(shops, 4, 4)
        when:
        def point = calc.calculate(dist)
        then:
        point.x == x
        point.y == y
        where:
        dist | x | y
        1    | 3 | 4
        2    | 2 | 2
        4    | 3 | 1
    }

}
