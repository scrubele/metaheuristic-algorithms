from criterias.aspiration_criteria import AspirationCriteria, OptimizationStrategy
from criterias.stopping_criteria import IterationStopCriteria
from memory_data.long_term_memory import Diversification
from memory_data.middle_term_memory import Intensification
from memory_data.short_term_memory import TabuQueue, MemoryStrategy
from solutions.locators import SolutionNeighbourLocator
from solutions.solutions import TravelerSalesmanProblemSolution


class TabuSearchAlgorithm:

    def __init__(self,
                 weights,
                 tabu_queue_size=5,
                 tabu_memory_strategy=MemoryStrategy.SAVE_IDS,
                 intensification_threshold_value=10,
                 diversification_threshold_value=20,
                 diversification_pick_number=3,
                 max_iterations=400,
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
        self.tabu_queue = TabuQueue(maxsize=tabu_queue_size, memory_strategy=tabu_memory_strategy)
        self.memory_frozen_dict = {}
        self.intensification = Intensification(size=self.weights_size,
                                               criteria_threshold_value=intensification_threshold_value,
                                               )
        self.diversification = Diversification(size=self.weights_size,
                                               threshold_number=diversification_threshold_value,
                                               pick_number=diversification_pick_number,
                                               )

    def objective_function(self):
        current_solution = self.best_solution
        current_iteration = 0
        while self.stopping_criteria.is_satisfied(current_iteration):
            print("\niteration:", current_iteration, "\tsolution:", current_solution)
            best_admissible_solution = self.find_best_neighbour(current_solution=current_solution)

            if self.aspiration_criteria.is_satisfied(best_admissible_solution, self.best_solution):
                self.best_solution = best_admissible_solution
                self.diversification.value = 0
            else:
                print("Not improving iteration: ", self.diversification.value)
                self.diversification.value += 1

            self.tabu_queue.add(solution=best_admissible_solution)
            current_solution = best_admissible_solution

            self.intensification.add(solution=self.best_solution)
            self.diversification.add(solution=current_solution)
            self.diversification.run(self.intensification)
            current_iteration += 1
        return self.best_solution

    def find_best_neighbour(self, current_solution):
        neighbour_locator = self.neighbour_locator(current_solution,
                                                   intensification=self.intensification,
                                                   diversification=self.diversification)
        candidate_neighbours = neighbour_locator.candidate_solutions
        print("Candidate neighbours: ", candidate_neighbours)
        best_admissible_solution = neighbour_locator.find_best_neighbour(tabu_list=self.tabu_queue.queue)
        return best_admissible_solution
