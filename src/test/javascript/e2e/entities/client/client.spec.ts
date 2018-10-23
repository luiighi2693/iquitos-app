/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClientComponentsPage, ClientDeleteDialog, ClientUpdatePage } from './client.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Client e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clientUpdatePage: ClientUpdatePage;
    let clientComponentsPage: ClientComponentsPage;
    let clientDeleteDialog: ClientDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Clients', async () => {
        await navBarPage.goToEntity('client');
        clientComponentsPage = new ClientComponentsPage();
        expect(await clientComponentsPage.getTitle()).to.eq('Clients');
    });

    it('should load create Client page', async () => {
        await clientComponentsPage.clickOnCreateButton();
        clientUpdatePage = new ClientUpdatePage();
        expect(await clientUpdatePage.getPageTitle()).to.eq('Create or edit a Client');
        await clientUpdatePage.cancel();
    });

    it('should create and save Clients', async () => {
        const nbButtonsBeforeCreate = await clientComponentsPage.countDeleteButtons();

        await clientComponentsPage.clickOnCreateButton();
        await promise.all([
            clientUpdatePage.setNameInput('name'),
            clientUpdatePage.setCodeInput('code'),
            clientUpdatePage.setAddressInput('address'),
            clientUpdatePage.setEmailInput('email'),
            clientUpdatePage.setCellphoneInput('cellphone'),
            clientUpdatePage.setBirthdayInput('2000-12-31'),
            clientUpdatePage.sexSelectLastOption(),
            clientUpdatePage.civilStatusSelectLastOption(),
            clientUpdatePage.setImageInput(absolutePath),
            clientUpdatePage.clientTypeSelectLastOption(),
            clientUpdatePage.userSelectLastOption(),
            clientUpdatePage.documentTypeSelectLastOption()
        ]);
        expect(await clientUpdatePage.getNameInput()).to.eq('name');
        expect(await clientUpdatePage.getCodeInput()).to.eq('code');
        expect(await clientUpdatePage.getAddressInput()).to.eq('address');
        expect(await clientUpdatePage.getEmailInput()).to.eq('email');
        expect(await clientUpdatePage.getCellphoneInput()).to.eq('cellphone');
        expect(await clientUpdatePage.getBirthdayInput()).to.eq('2000-12-31');
        expect(await clientUpdatePage.getImageInput()).to.endsWith(fileNameToUpload);
        await clientUpdatePage.save();
        expect(await clientUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await clientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Client', async () => {
        const nbButtonsBeforeDelete = await clientComponentsPage.countDeleteButtons();
        await clientComponentsPage.clickOnLastDeleteButton();

        clientDeleteDialog = new ClientDeleteDialog();
        expect(await clientDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Client?');
        await clientDeleteDialog.clickOnConfirmButton();

        expect(await clientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
