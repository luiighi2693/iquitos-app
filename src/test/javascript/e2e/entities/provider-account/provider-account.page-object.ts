import { element, by, ElementFinder } from 'protractor';

export class ProviderAccountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-provider-account div table .btn-danger'));
    title = element.all(by.css('jhi-provider-account div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ProviderAccountUpdatePage {
    pageTitle = element(by.id('jhi-provider-account-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    statusSelect = element(by.id('field_status'));
    bankInput = element(by.id('field_bank'));
    accountNameInput = element(by.id('field_accountName'));
    accountNumberInput = element(by.id('field_accountNumber'));
    initDateInput = element(by.id('field_initDate'));
    providerSelect = element(by.id('field_provider'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setBankInput(bank) {
        await this.bankInput.sendKeys(bank);
    }

    async getBankInput() {
        return this.bankInput.getAttribute('value');
    }

    async setAccountNameInput(accountName) {
        await this.accountNameInput.sendKeys(accountName);
    }

    async getAccountNameInput() {
        return this.accountNameInput.getAttribute('value');
    }

    async setAccountNumberInput(accountNumber) {
        await this.accountNumberInput.sendKeys(accountNumber);
    }

    async getAccountNumberInput() {
        return this.accountNumberInput.getAttribute('value');
    }

    async setInitDateInput(initDate) {
        await this.initDateInput.sendKeys(initDate);
    }

    async getInitDateInput() {
        return this.initDateInput.getAttribute('value');
    }

    async providerSelectLastOption() {
        await this.providerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async providerSelectOption(option) {
        await this.providerSelect.sendKeys(option);
    }

    getProviderSelect(): ElementFinder {
        return this.providerSelect;
    }

    async getProviderSelectedOption() {
        return this.providerSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ProviderAccountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-providerAccount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-providerAccount'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
