package enery.use_case.health_assessment;

import enery.entity.healthAssessment.AssessmentOverview;
import enery.entity.healthAssessment.DietaryPatternAssessment;
import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.use_case.tracking.TrackingOutputData;

import java.util.UUID;

public class HealthAssessmentInteractor implements HealthAssessmentInputBoundary {

    private final HealthAssessmentOutputBoundary outputBoundary;

    private final HealthAssessmentDataAccessInterface healthDataAccess;

    public HealthAssessmentInteractor(HealthAssessmentOutputBoundary outputBoundary,
                                      HealthAssessmentDataAccessInterface HealthAssessmentDataAccessInterface) {
        this.outputBoundary = outputBoundary;
        this.healthDataAccess = HealthAssessmentDataAccessInterface;
    }

    @Override
    public void execute(HealthAssessmentInputData inputData) {
        try {
            String assessmentId = UUID.randomUUID().toString();
            String userId = inputData.getUserId();
            java.time.LocalDate endDate = java.time.LocalDate.now();
            java.time.LocalDate startDate = endDate.minusDays(7);

            TrackingOutputData trackingData = healthDataAccess.getTrackingData(userId, startDate, endDate);
            if (trackingData == null) {
                outputBoundary.prepareFailView("No tracking records found for this user. Please log data first.");
                return;
            }

            AssessmentOverview overview = new AssessmentOverview(assessmentId, userId);
            // ===========================
            // Dietary Pattern Assessment
            // ===========================
            DietaryPatternAssessment dietaryAssessment = new DietaryPatternAssessment(assessmentId, userId);

            NutrientStandards nutrientStandards = healthDataAccess.getNutrientStandards();
            NutrientComparer comparer = new NutrientComparer();
            NutrientComparer.DietaryEvaluationSummary dietSummary =
                    comparer.evaluateDiet(trackingData, nutrientStandards);

            // Into Entity
            dietaryAssessment.setDietScore(dietSummary.averageScore);
            for (String insight : dietSummary.insights) {
                dietaryAssessment.addDietInsight(insight);
            }
            for (String item : dietSummary.actionableItems) {
                dietaryAssessment.addDietActionableItem(item);
            }

            // overview
            overview.setDietaryPatternAssessment(dietaryAssessment);

             HealthAssessmentOutputData outputData = new HealthAssessmentOutputData(overview.getAssessmentId(),
                     overview.getOverallScore(),
                     overview.getSeverity(),
                     dietaryAssessment.getDietScore(),
                     dietaryAssessment.getDietInsights(),
                     dietaryAssessment.getDietActionableItems());
             outputBoundary.prepareSuccessView(outputData);
        } catch (Exception e) {
            outputBoundary.prepareFailView("Fail to assess: " + e.getMessage());
        }
    }
}


//            // ==========================================
//            // 模块 B: 评估 Exercise Pattern (运动)
//            // ==========================================
//            ExercisePatternAssessment exerciseAssessment = new ExercisePatternAssessment(assessmentId, userId);

//            // 执行运动评估运算 (逻辑见下方私有方法)
//            evaluateExercise(actualExerciseBurned, exerciseStandard, exerciseAssessment);
//
//            // 组装到 Overview，此时 Overview 内部会自动调用 updateAssessmentState() 计算总分
//            overview.setExercisePatternAssessment(exerciseAssessment);
//
//
//            // ==========================================
//            // 3. 封装 OutputData 传递给 Presenter
//            // ==========================================
//            HealthAssessmentOutputData outputData = new HealthAssessmentOutputData(
//                    overview.getAssessmentId(),
//                    overview.getOverallScore(),
//                    overview.getSeverity(),
//                    dietaryAssessment.getDietScore(),
//                    dietaryAssessment.getDietInsights(),
//                    dietaryAssessment.getDietActionableItems(),
//                    exerciseAssessment.getExerciseScore(),
//                    exerciseAssessment.getExerciseInsights(),
//                    exerciseAssessment.getExerciseActionableItems()
//            );
//
//            // 通知 Presenter 准备成功视图
//            outputBoundary.prepareSuccessView(outputData);
//
//        } catch (Exception e) {
//            // 发生异常时，通知 Presenter 准备失败视图
//            outputBoundary.prepareFailView("生成健康评估失败: " + e.getMessage());
//        }
//    }

//    /**
//     * 辅助方法：评估运动量，使用与营养素相同的数学比例逻辑
//     */
//    private void evaluateExercise(double actualBurned, double standard, ExercisePatternAssessment assessment) {
//        if (standard <= 0) {
//            assessment.setExerciseScore(100);
//            return;
//        }
//
//        double diff = actualBurned - standard;
//        double percentageDiff = Math.abs(diff) / standard;
//
//        int rating = 100;
//        String metricName = "运动消耗";
//        String unit = " kcal";
//
//        if (actualBurned > standard) {
//            if (percentageDiff >= 0.5) {
//                rating = 0;
//                assessment.addExerciseInsight(metricName + " 过高了，有健康风险");
//            } else {
//                rating = 80;
//                assessment.addExerciseInsight(metricName + " 稍高，但暂无健康风险");
//            }
//            assessment.addExerciseActionableItem(metricName + " 需减少 (" + String.format("%.1f", diff) + unit + ")");
//
//        } else if (actualBurned < standard) {
//            if (percentageDiff >= 0.5) {
//                rating = 0;
//                assessment.addExerciseInsight(metricName + " 过低了，有健康风险");
//                assessment.addExerciseActionableItem(metricName + " 需增加 (" + String.format("%.1f", Math.abs(diff)) + unit + ")");
//            } else {
//                rating = 80;
//            }
//        }
//
//        assessment.setExerciseScore(rating);
//