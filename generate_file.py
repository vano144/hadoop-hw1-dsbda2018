import string
import random
import os
from uuid import uuid4

example_non_ascii = ''.join(tuple(chr(l) for l in range(400, 500) if chr(l).isprintable()))


def generate_ascii_word(letters_choices:str,
                        min_len: int = 5, max_len: int = 10) -> str:
    """
    Generate random words only ascii in range from min to max len.
    :param letters_choices: set of letters for word
    :param min_len:
    :param max_len:
    :return:
    """
    return ''.join([random.choice(letters_choices) for _ in
                    range(random.randint(min_len, max_len))])


def random_write_to_file(f, letters_choices, *args, **kwargs):
    """
    Random amount write words to file.
    :param f: fd
    :param letters_choices:
    :param args:
    :param kwargs:
    :return:
    """
    for _ in range(4, random.randint(5, 100)):
        f.write(generate_ascii_word(letters_choices, *args, **kwargs))
        if random.randint(3, 42) % 3 == 0:
            f.write("\n")


def generate_big_random_letters(filename, size):
    """
    generate big random letters/alphabets to a file
    :param filename: the filename
    :param size: the size in bytes
    :return: void
    """

    with open(filename, 'w') as f:
        # generate good words
        random_write_to_file(f, letters_choices=string.ascii_letters)

        # generate not only ascii words
        random_write_to_file(f, letters_choices=string.printable + example_non_ascii)

        # generate short words
        random_write_to_file(f, letters_choices=string.ascii_letters, min_len=1, max_len=3)


def get_file_path():
    file_directory = os.path.join(os.getcwd(), 'input')
    if not os.path.exists(file_directory):
        os.makedirs(file_directory)
    return os.path.join(file_directory, f'{str(uuid4())}.txt')


if __name__ == '__main__':
    generate_big_random_letters(get_file_path(), 1024 * 1024)