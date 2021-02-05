from copy import deepcopy

from criterias.aspiration_criteria import AspirationCriteria
from strategies.neighbourhood_strategies import NeighbourhoodGenerationStrategy
from memory_lists.tabu_list import TabuElement


class SolutionNeighbourLocator:

    def __init__(self, initial_solution, aspiration_criteria=AspirationCriteria,
                 neighbourhood_criteria=NeighbourhoodGenerationStrategy.SWAP):
        self.initial_solution = initial_solution
        self.aspiration_criteria = aspiration_criteria
        self.neighbourhood_criteria = neighbourhood_criteria
        self.candidate_solutions = self.neighbourhood_criteria.generate_neighbours(self.initial_solution)

    def find_best_neighbour(self, tabu_list):
        candidate_solution_list = deepcopy(self.candidate_solutions)
        # difference_list = set(tabu_list).symmetric_difference(candidate_solution_list)
        best_admissible_solution = min(candidate_solution_list)
        print("best", best_admissible_solution)
        # if isinstance(best_admissible_solution, TabuElement):
        #     return None
        return best_admissible_solution
