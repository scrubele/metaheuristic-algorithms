from strategies.memory_strategies import MemoryStrategy


class IntensificationMatrix:

    def __init__(self, size):
        self.size = size
        self.matrix = self.initialize_matrix()
        self.memory_strategy = MemoryStrategy.SAVE_POSITIONS

    def initialize_matrix(self):
        return [[0 for col in range(self.size)] for row in range(self.size)]

    def __add(self, pair):
        first = pair[0]
        last = pair[1]
        print("first", first,"last", last, "previous", self.matrix[first][last] )
        self.matrix[first][last] += 1
        self.matrix[last][first] += 1

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        print("picked_value", picked_value)
        self.__add(picked_value)

    def __repr__(self):
        str = "\n"
        for row in self.matrix:
            for val in row:
                str += '{:4}'.format(val)
            str += "\n"
        return str
