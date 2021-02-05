import math

from criterias.aspiration_criteria import AspirationCriteria, Optimization
from memory_lists.intensification_matrix import IntensificationMatrix
from solutions.locators import SolutionNeighbourLocator
from solutions.solutions import TravelerSalesmanProblemSolution
from criterias.stop_criteria import IterationStopCriteria
from memory_lists.tabu_list import TabuQueue, MemoryStrategy


class TabuSearchAlgorithm:

    def __init__(self,
                 weights,
                 tabu_queue_size=5,
                 tabu_memory_strategy=MemoryStrategy.SAVE_IDS,
                 max_iterations=200,
                 optimization=Optimization.MINIMIZATION,
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
        tabu_queue_size  = int(math.sqrt(self.weights_size))
        self.tabu_queue = TabuQueue(maxsize=tabu_queue_size, memory_strategy=tabu_memory_strategy)
        self.intensification_matrix = IntensificationMatrix(size=self.weights_size)

    def objective_function(self):
        current_solution = self.best_solution
        previous_solution = None
        current_iteration = 0
        while self.stopping_criteria.is_best_solution(current_iteration, previous_solution, current_solution):
            print("\niteration ", current_iteration, "\tsolution:", current_solution)
            neighbour_locator = self.neighbour_locator(current_solution)
            candidate_neighbours = neighbour_locator.candidate_solutions
            print("candidate neighbours", candidate_neighbours)
            best_admissible_solution = neighbour_locator.find_best_neighbour(tabu_list=self.tabu_queue.queue)
            # print(best_admissible_solution, self.best_solution)
            if self.aspiration_criteria.is_satisfied(best_admissible_solution, self.best_solution):
                previous_solution = self.best_solution
                self.best_solution = best_admissible_solution
            self.tabu_queue.add(solution=best_admissible_solution)
            current_solution = best_admissible_solution
            self.intensification_matrix.add(solution=current_solution)
            print('Tabu queue', self.tabu_queue.queue)
            current_iteration += 1
        print("intensification", self.intensification_matrix)
        return self.best_solution
