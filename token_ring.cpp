#include "token_ring.h"
#include <string>
#include <iostream>


using namespace std;

Node* new_Node(token t){
    Node* tmp = new Node();
    tmp -> t = t;
    tmp -> next = NULL;
    return tmp;
}

Queue* create_Queue(){
    Queue* q = new Queue();
    q -> first = NULL;
    q -> last = NULL;
    return q;
}

void queue_add(Queue* q, token t){
    Node * node = new_Node(t);
    
    if(q -> first == NULL){
        q -> first = node;
        q -> last = node;
    }
    else {
        q -> last -> next = node;
        q -> last  = node; 
    }
}

Node* queue_del(Queue* q){
    if(q -> first == NULL ){
        return NULL;
    }
    else {
        Node* tmp = q -> first;
        q -> first = q -> first -> next;
    }

    if(q -> first == NULL){
        q -> last = NULL;
    }
    return tmp;
}

token create_token(unsigned char TTL, string destination, string source, string data){
    token t = new token();
    t.TTL = TTL;
    if(destination){
        t.destination = destination;
    }
    if(source){
        t.source = source;
    }
    if(data){
        t.data = data;
    }
    return t;
}