#ifndef TOKENRING_H
#define TOKENRING_H

#include <string>

using std::string;

struct token {
    unsigned char TTL;
    string destination;
    string source;
    string data;
};

struct Node{
    token t;
    struct Node* next;
};

struct Queue{
    struct Node* first, last;
};


Node* new_Node(token t);

Queue* create_Queue();

void queue_add(Queue* q, token t); // creates new node and then adds it to queue as last node (uses new_Node(token t))

Node* queue_del(Queue* q); // deletes first node from q

token create_token(unsigned char TTL, string destination, string source, string data); //creates new Token

void send_token(token t);

#endif