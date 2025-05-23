package f5.health.app.service.healthreport.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static f5.health.app.entity.meal.Meal.MEAL_TYPE_COUNT;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@ToString
@Schema(description = "식단(아침/점심/저녁/간식..) 기록 컬렉션", requiredMode = REQUIRED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MealsRequest {
    
    @Schema(description = "식사 리스트")
    @Valid
    @NotNull(message = "식사를 최소 한개 이상 작성해 주세요.")
    @Size(min = 1, max = MEAL_TYPE_COUNT, message = "식사는 하루에 " + MEAL_TYPE_COUNT + "개로 분류됩니다.")
    private List<MealRequest> mealRequestList;


    /** 모든 식단에 기록된 음식의 foodCode를 Set으로 모아서 반환 */
    @Schema(hidden = true)
    public Set<String> getEatenFoodCodeSet() {
        return this.mealRequestList.stream()
                .flatMap(mealRequest -> mealRequest.getMealFoodRequestList().stream()) // flatMap: 하나의 연속된 스트림으로 만듦
                .map(MealFoodRequest::getFoodCode)
                .collect(Collectors.toSet());
    }

    /** 식사 횟수 */
    @Schema(hidden = true)
    public int getMealCount() {
        return this.mealRequestList.size();
    }

    /** 식사당 메뉴 기록 개수가 담긴 리스트 반환 */
    @Schema(hidden = true)
    public List<Integer> getMenuCountsPerMeal() {
        return this.mealRequestList.stream()
                .map(meal -> meal.getMealFoodRequestList().size())
                .toList();
    }
}
