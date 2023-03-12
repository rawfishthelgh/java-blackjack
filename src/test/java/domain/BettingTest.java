package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BettingTest {

    @ParameterizedTest
    @ValueSource(ints = {-1,0})
    void 베팅금액은_0원을_초과해야_한다(int bettingMoney) {
        assertThatThrownBy(
            ()->  new Betting(bettingMoney, 20000)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수익에서_베팅금액을_공제해_반환한다() {
        Betting betting = new Betting(10000, 20000);
        assertThat(betting.calculateFinalProfit()).isEqualTo(10000);
    }

    @Test
    void 베팅금액의_150퍼센트를_보너스로_줄_수_있다() {
        Betting betting = new Betting(10000, 0);
        assertThat(betting.giveBonus().calculateFinalProfit()).isEqualTo(5000);
    }


    @Test
    void 딜러_패배시_플레이어는_베팅금액_두배를_받는다() {
        Betting betting = new Betting(10000, 0);
        Betting afterGame = betting.calculateProfitByResult(Result.LOSE);
        assertThat(afterGame.calculateFinalProfit()).isEqualTo(10000);
    }

    @Test
    void 무승부시_베팅금액을_돌려_받는다() {
        Betting betting = new Betting(10000, 0);
        Betting afterGame = betting.calculateProfitByResult(Result.DRAW);
        assertThat(afterGame.calculateFinalProfit()).isEqualTo(0);
    }

    @Test
    void 딜러_승리시_베팅금액을_빼앗긴다() {
        Betting betting = new Betting(10000, 0);
        Betting afterGame = betting.calculateProfitByResult(Result.WIN);
        assertThat(afterGame.calculateFinalProfit()).isEqualTo(-10000);
    }
}
