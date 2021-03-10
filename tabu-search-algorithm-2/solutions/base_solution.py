from filecmp import cmp


class IntegerRepresentation:

    def __init__(self):
        self.encoding = []
        self.value = 0.0

    def get_size(self) -> int:
        return len(self.encoding)

    def __str__(self):
        return "Encoding: " + str(self.encoding) + "|" + " value:" + str(self.value)

    def __repr__(self):
        return "Encoding: " + str(self.encoding) + "|" + " value:" + str(self.value)

    def __eq__(self, other):
        if not isinstance(other, IntegerRepresentation):
            # don't attempt to compare against unrelated types
            return NotImplemented
        return self.encoding == other.encoding and self.value == other.value

    def __key(self):
        return self.value

    def __hash__(self):
        return hash(self.__key())

    def __cmp__(self, other):
        return cmp(self.value, other.value)

    def __lt__(self, other):
        return self.value < other.value

    def __gt__(self, other):
        return self.value > other.value

    def __le__(self, other):
        return self.value <= other.value

    def __ge__(self, other):
        return self.value >= other.value
