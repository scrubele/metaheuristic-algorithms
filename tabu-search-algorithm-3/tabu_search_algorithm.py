import random
from solutions.travelling_salesman_problem_solution import TravelerSalesmanProblemSolution

DEBUG = True


class TabuSearchAlgorithm:

    def __init__(self,
                 weights,
                 tabu_queue_size=5,
                 tabu_memory_strategy=MemoryStrategy.SAVE_IDS,
                 intensification_threshold_value=2,
                 diversification_threshold_value=20,
                 diversification_pick_number=3,
                 intensification_pick_number=3,
                 max_iterations=600,
                 optimization=OptimizationStrategy.MINIMIZATION,
                 solution_class=TravelerSalesmanProblemSolution,
                 neighbour_locator=SolutionNeighbourLocator,
                 stopping_criteria=IterationStopCriteria,
                 aspiration_criteria=AspirationCriteria):
        self.weights = weights
        self.weights_size = len(self.weights)
        self.best_solution = solution_class(weights=self.weights)

        self.neighbour_locator = neighbour_locator
        self.stopping_criteria = stopping_criteria(max_iterations=max_iterations)
        self.aspiration_criteria = aspiration_criteria(criteria=optimization)

        self.short_term_memory = TabuQueue(maxsize=tabu_queue_size, memory_strategy=tabu_memory_strategy)
        self.middle_term_memory = Intensification(size=self.weights_size,
                                                  threshold_number=intensification_threshold_value,
                                                  criteria_threshold_value=intensification_threshold_value,
                                                  pick_number=intensification_pick_number)
        self.long_term_memory = Diversification(size=self.weights_size,
                                                threshold_number=diversification_threshold_value,
                                                pick_number=diversification_pick_number)

    def objective_function(self):
        result_plot_list = []
        current_solution = self.best_solution
        self.long_term_memory.alpha = 0.09*current_solution.value
        current_iteration = 0
        while self.stopping_criteria.is_satisfied(current_iteration):
            # print("iteration:", current_iteration, "\tsolution:", current_solution)
            best_admissible_solution = self.find_best_neighbour(current_solution=current_solution)
            if self.aspiration_criteria.is_satisfied(best_admissible_solution, self.best_solution):
                self.best_solution = best_admissible_solution
                self.long_term_memory.unsuccess_iterations = current_iteration / 100
                self.middle_term_memory.unsuccess_iterations +=1
            else:
                self.long_term_memory.unsuccess_iterations += 1
                self.middle_term_memory.unsuccess_iterations = 0
            if self.long_term_memory.unsuccess_iterations > 2:
                self.long_term_memory.alpha = 0.009*self.best_solution.value
                # mu = random.random()*0.5+1.5
                # self.long_term_memory.alpha = self.long_term_memory.alpha / mu
            self.short_term_memory.add(solution=best_admissible_solution)
            # print('TabuQueue: ', self.short_term_memory.queue)
            current_solution = best_admissible_solution
            # print(current_solution, best_admissible_solution)
            self.middle_term_memory.add(solution=self.best_solution)
            # print("Frozen values: ", self.middle_term_memory.frozen_values)
            # self.long_term_memory.add(solution=current_solution)
            # print("Pick values: ", self.long_term_memory.should_pick_values)
            # print(self.middle_term_memory.unsuccess_iterations)
            self.middle_term_memory.run(self.best_solution, iteration=current_iteration)
            self.long_term_memory.run(self.best_solution, iteration=current_iteration)
            result_plot_list.append(current_solution)
            current_iteration += 1
        # print(self.middle_term_memory)
        # print(self.long_term_memory)
        return self.best_solution, result_plot_list

    def find_best_neighbour(self, current_solution):
        neighbour_locator = self.neighbour_locator(current_solution,
                                                   intensification=self.middle_term_memory,
                                                   diversification=self.long_term_memory)
        candidate_neighbours = neighbour_locator.candidate_solutions
        # print("Candidate neighbours: ", candidate_neighbours)
        best_admissible_solution = neighbour_locator.find_best_neighbour(tabu_list=self.short_term_memory.queue)
        # print("Best admissible solution:", best_admissible_solution.swap)
        return best_admissible_solution
