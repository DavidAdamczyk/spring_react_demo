import React from "react";
import {Button, Card, Col, Container, Form, ListGroup, Row, Alert} from "react-bootstrap";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            firstName: "",
            lastName: "",
            age: 0,
            personId: 0,
            error: {},
        };
    }

    componentDidMount() {
        const axios = require('axios').default;
        axios.get('http://localhost:8080/persons')
            .then((response) => {
                this.setState({
                    'data': response.data
                })
                //console.log(response.data);
                //console.log(response.status);
                //console.log(response.statusText);
                //console.log(response.headers);
                //console.log(response.config);
            });
        //console.log(this.state.response);
        //console.log(this.state.error);
    }

    onChange = (e, name) => {
        //console.log("---", e, name)
        this.setState({
            [name]: e
        })
    }

    createNew = (e) => {
        e.preventDefault();
        const axios = require('axios').default;
        axios({
            method: 'put',
            url: 'http://localhost:8080/persons/' + this.state.personId,
            data: {
                "firstName": this.state.firstName,
                "lastName": this.state.lastName,
                "age": this.state.age
            },
            headers:{
                'Access-Control-Allow-Origin':'*',
                'Content-Type': 'application/json;charset=UTF-8',
            }
        }).then(response => {
            console.log(response)
            if (response.data.errors){
                this.setState({
                    error: response.data.errors
                })
            } else {
                this.setState({
                    error: {},
                    firstName: "",
                    lastName: "",
                    age: 0,
                })
            }
        }).catch(error => {
            console.log('Error: ', error);
        });
    }

    render() {
        const DisplayError = (error) => {
            return Object.keys(error.children).map((item,index) => {
                //console.log(item, index)
                return(
                    <Alert key={index} variant="danger">
                        {error.children[item]}
                    </Alert>
                )
            })
        }

        const DisplayItems = (data) => {
            const list = data.children.map(item => <CustomItem key={item.id}>{item}</CustomItem>)
            return (
                <ListGroup variant="flush">
                    {list}
                </ListGroup>
            )
        }

        const CustomItem = (data) => {
            const item = data.children;
            return <ListGroup.Item>
                <Col>
                    <Row><b>id:</b> {item.id} </Row>
                    <Row><b> firstName:</b> {item.firstName} </Row>
                    <Row><b> lastName:</b> {item.lastName} </Row>
                    <Row><b> age:</b> {item.age} </Row>
                </Col>
            </ListGroup.Item>;
        };

        return (
            <Container>
                <h1>React REST Demo for Person Entity</h1>
                <Card>
                    <DisplayError>{this.state.error}</DisplayError>
                    <DisplayItems>{this.state.data}</DisplayItems>
                </Card>
                <Card>
                    <Card.Header>Create or Update</Card.Header>
                    <Card.Body>
                        <Card.Title>Create or Update new item</Card.Title>
                        <Form onSubmit={this.createNew}>
                            <Form.Group controlId="personId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter personId"
                                    value={this.state.personId}
                                    onChange={(e) => this.onChange(e.target.value, 'personId')}
                                />
                            </Form.Group>

                            <Form.Group controlId="firstNameId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter firstName"
                                    value={this.state.firstName}
                                    onChange={(e) => this.onChange(e.target.value, 'firstName')}
                                />
                            </Form.Group>

                            <Form.Group controlId="lastNameId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter lastName"
                                    value={this.state.lastName}
                                    onChange={(e) => this.onChange(e.target.value, 'lastName')}
                                />
                            </Form.Group>

                            <Form.Group controlId="ageId">
                                <Form.Control
                                    type="text"
                                    placeholder="Enter age"
                                    value={this.state.age}
                                    onChange={(e) => this.onChange(e.target.value, 'age')}
                                />
                            </Form.Group>

                            <Button variant="primary" type="submit">Create or Update</Button>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        );
    }
}
export default App;