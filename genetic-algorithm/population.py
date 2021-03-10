import numpy as np
import matplotlib.pyplot as plt
import random

class BasePopulation:
    def __init__(self, bag):
        self.bag = bag
        self.parents = []
        self.score = 0
        self.best = None

    def fitness(self, chromosome):
        return 0

    def select(self, tournament=4):
        fit = self.evaluate()
        # print("self.bag", self.bag)
        while len(self.parents) < tournament:
            idx = np.random.randint(0, len(fit))
            if fit[idx] > np.random.rand():
                self.parents.append(self.bag[idx])
        self.parents = np.asarray(self.parents)
        print("parents", self.parents)

    def crossover(self, crossover_probability=0.1):
        children = []
        count, size = self.parents.shape
        for _ in range(len(self.bag)):
            if np.random.rand() > crossover_probability:
                children.append(
                    list(self.parents[np.random.randint(count, size=1)[0]])
                )
            else:
                # print("self.parents", self.parents)
                parent1, parent2 = self.parents[
                    np.random.randint(count, size=2), :
                ]
                # print("parent1", parent1,"parent2", parent2)
                idx = np.random.choice(range(size), size=2, replace=False)
                # print(idx)
                start, end = min(idx), max(idx)
                child = [None] * size
                for i in range(start, end + 1, 1):
                    child[i] = parent1[i]
                # print(child)
                pointer = 0
                for i in range(size):
                    if child[i] is None:
                        # print("pointer", pointer, "parent2[pointer]", parent2[pointer])
                        while parent2[pointer] in child and pointer<size -1:
                            # print("pointer", pointer)
                            pointer += 1
                        child[i] = parent2[pointer]
                children.append(child)
        return children


    def evaluate(self):
        distances = np.asarray([self.fitness(chromosome) for chromosome in self.bag])
        self.score = np.min(distances)
        self.best = self.bag[distances.tolist().index(self.score)]
        self.parents.append(self.best)
        # print("Self.parents", self.parents)
        if False in (distances[0] == distances):
            distances = np.max(distances) - distances
        return distances / np.sum(distances)

    def mutate(self, crossover_probability=0.1, mutation_probability=0.1):
        next_bag = []
        children = self.crossover(crossover_probability)
        for child in children:
            if np.random.rand() < mutation_probability:
                next_bag.append(self.swap(child))
            else:
                next_bag.append(child)
        return next_bag


class TSPPopulation(BasePopulation):
    def __init__(self, bag, adjacency_mat):
        super(TSPPopulation, self).__init__(bag)
        self.adjacency_mat = adjacency_mat

    def fitness(self, chromosome):
        fitness_value = sum(
            [
                self.adjacency_mat[chromosome[i], chromosome[i + 1]]
                for i in range(len(chromosome) - 1)
            ]
        )
        fitness_value += self.adjacency_mat[
            chromosome[len(chromosome) - 1], chromosome[0]
        ]
        return fitness_value

    def swap(self, chromosome):
        a, b = np.random.choice(len(chromosome), 2)
        chromosome[a], chromosome[b] = (
            chromosome[b],
            chromosome[a],
        )
        return chromosome

    @classmethod
    def get_random_population(cls, cities, adjacency_mat, n_population):
        return cls(
            np.asarray([np.random.permutation(cities) for _ in range(n_population)]),
            adjacency_mat,
        )


class OneMaxPopulation(BasePopulation):
    def __init__(self, bag, adjacency_mat):
        super(OneMaxPopulation, self).__init__(bag)
        self.adjacency_mat = adjacency_mat

    def fitness(self, chromosome):
        fitness_value = sum(chromosome)
        return fitness_value


    def swap(self, chromosome):
        random_item = int(random.random() * self.adjacency_mat)
        chromosome[random_item] = 1
        return chromosome

    @classmethod
    def get_random_population(cls, adjacency_mat, n_population, cities=None):
        bag =  np.asarray([set_ones_zeros(adjacency_mat) for _ in range(n_population)])
        # print(bag)
        return cls(
            bag,
            adjacency_mat,
        )

    def evaluate(self):
        distances = np.asarray([self.fitness(chromosome) for chromosome in self.bag])
        self.score = np.max(distances)
        self.best = self.bag[distances.tolist().index(self.score)]
        self.parents.append(self.best)
        # print("Self.parents", self.parents)
        if False in (distances[0] == distances):
            distances = np.max(distances) - distances
        return distances / np.sum(distances)

def set_ones_zeros(list_size):
    one_count = random.randint(1, int(list_size/2))
    zero_count = list_size - one_count
    my_list = [0]*zero_count + [1]*one_count
    random.shuffle(my_list)
    return my_list