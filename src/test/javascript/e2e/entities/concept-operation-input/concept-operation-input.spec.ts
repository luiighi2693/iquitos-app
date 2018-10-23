/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ConceptOperationInputComponentsPage,
    ConceptOperationInputDeleteDialog,
    ConceptOperationInputUpdatePage
} from './concept-operation-input.page-object';

const expect = chai.expect;

describe('ConceptOperationInput e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let conceptOperationInputUpdatePage: ConceptOperationInputUpdatePage;
    let conceptOperationInputComponentsPage: ConceptOperationInputComponentsPage;
    let conceptOperationInputDeleteDialog: ConceptOperationInputDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ConceptOperationInputs', async () => {
        await navBarPage.goToEntity('concept-operation-input');
        conceptOperationInputComponentsPage = new ConceptOperationInputComponentsPage();
        expect(await conceptOperationInputComponentsPage.getTitle()).to.eq('Concept Operation Inputs');
    });

    it('should load create ConceptOperationInput page', async () => {
        await conceptOperationInputComponentsPage.clickOnCreateButton();
        conceptOperationInputUpdatePage = new ConceptOperationInputUpdatePage();
        expect(await conceptOperationInputUpdatePage.getPageTitle()).to.eq('Create or edit a Concept Operation Input');
        await conceptOperationInputUpdatePage.cancel();
    });

    it('should create and save ConceptOperationInputs', async () => {
        const nbButtonsBeforeCreate = await conceptOperationInputComponentsPage.countDeleteButtons();

        await conceptOperationInputComponentsPage.clickOnCreateButton();
        await promise.all([
            conceptOperationInputUpdatePage.setValueInput('value'),
            conceptOperationInputUpdatePage.setMetaDataInput('metaData')
        ]);
        expect(await conceptOperationInputUpdatePage.getValueInput()).to.eq('value');
        expect(await conceptOperationInputUpdatePage.getMetaDataInput()).to.eq('metaData');
        await conceptOperationInputUpdatePage.save();
        expect(await conceptOperationInputUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await conceptOperationInputComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ConceptOperationInput', async () => {
        const nbButtonsBeforeDelete = await conceptOperationInputComponentsPage.countDeleteButtons();
        await conceptOperationInputComponentsPage.clickOnLastDeleteButton();

        conceptOperationInputDeleteDialog = new ConceptOperationInputDeleteDialog();
        expect(await conceptOperationInputDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Concept Operation Input?'
        );
        await conceptOperationInputDeleteDialog.clickOnConfirmButton();

        expect(await conceptOperationInputComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
