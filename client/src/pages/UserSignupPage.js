import React from "react";

export class UserSignupPage extends React.Component {
    state = {
        displayName: '',
        username: '',
        password: '',
        passwordRepeat: '',
        pendingApiCall: false,
    }

    onChangeDisplayName = (event) => {
        const value = event.target.value;
        this.setState({ displayName: value });
    }
    onChangeUsername = (event) => {
        const value = event.target.value;
        this.setState({ username: value });
    }
    onChangePassword = (event) => {
        const value = event.target.value;
        this.setState({ password: value });
    }
    onChangePasswordRepeat = (event) => {
        const value = event.target.value;
        this.setState({ passwordRepeat: value });
    }
    onClickSignup = () => {
        const user = {
            displayName: this.state.displayName,
            username: this.state.username,
            password: this.state.password
        }
        this.setState({pendingApiCall: true});
        this.props.actions.postSignup(user).then(response => {
            this.setState({ pendingApiCall: false});
        });
    }
    render() {
        return (
            <div className="container">
                <h1 className="text-center">Sign Up</h1>
                <div className="col-12 mb-3">
                    <label>Informe o seu nome</label>
                    <input className="form-control" 
                        type="text" placeholder="Informe o seu nome"
                        onChange={this.onChangeDisplayName}
                        value={this.state.displayName} />
                </div>
                <div className="col-12 mb-3">
                    <label>Informe o usuário</label>
                    <input className="form-control" 
                        type="text" placeholder="Informe o usuário"
                        onChange={this.onChangeUsername}
                        value={this.state.username} />
                </div>
                <div className="col-12 mb-3">
                    <label>Informe a sua senha</label>
                    <input className="form-control" 
                        type="password" placeholder="Informe a sua senha"
                        onChange={this.onChangePassword}
                        value={this.state.password} />
                </div>
                <div className="col-12 mb-3">
                    <label>Confirme sua senha</label>
                    <input className="form-control" 
                        type="password" placeholder="Confirme sua senha"
                        onChange={this.onChangePasswordRepeat}
                        value={this.state.passwordRepeat} />
                </div>
                <div className="text-center">
                    <button disabled={this.state.pendingApiCall} className="btn btn-primary" onClick={this.onClickSignup}
                    >
                        {this.state.pendingApiCall &&(
                            <div class="spinner-border text-light-spinner spinner-border-sm mr-sm-1" role="status">
                                <span class="visually-hidden">Aguarde...</span>
                            </div>
                        )}
                        Cadastrar
                    </button>
                </div>
            </div>
        );

        
    }
}
UserSignupPage.defaultProps = {
    actions: {
        postSignup: () =>
            new Promise((resolve, reject) =>{
                resolve({});
            })
    }
}
export default UserSignupPage;