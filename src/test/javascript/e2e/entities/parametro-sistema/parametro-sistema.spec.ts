/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ParametroSistemaComponentsPage, ParametroSistemaDeleteDialog, ParametroSistemaUpdatePage } from './parametro-sistema.page-object';

const expect = chai.expect;

describe('ParametroSistema e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let parametroSistemaUpdatePage: ParametroSistemaUpdatePage;
    let parametroSistemaComponentsPage: ParametroSistemaComponentsPage;
    let parametroSistemaDeleteDialog: ParametroSistemaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ParametroSistemas', async () => {
        await navBarPage.goToEntity('parametro-sistema');
        parametroSistemaComponentsPage = new ParametroSistemaComponentsPage();
        expect(await parametroSistemaComponentsPage.getTitle()).to.eq('Parametro Sistemas');
    });

    it('should load create ParametroSistema page', async () => {
        await parametroSistemaComponentsPage.clickOnCreateButton();
        parametroSistemaUpdatePage = new ParametroSistemaUpdatePage();
        expect(await parametroSistemaUpdatePage.getPageTitle()).to.eq('Create or edit a Parametro Sistema');
        await parametroSistemaUpdatePage.cancel();
    });

    it('should create and save ParametroSistemas', async () => {
        const nbButtonsBeforeCreate = await parametroSistemaComponentsPage.countDeleteButtons();

        await parametroSistemaComponentsPage.clickOnCreateButton();
        await promise.all([parametroSistemaUpdatePage.setNombreInput('nombre')]);
        expect(await parametroSistemaUpdatePage.getNombreInput()).to.eq('nombre');
        await parametroSistemaUpdatePage.save();
        expect(await parametroSistemaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await parametroSistemaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ParametroSistema', async () => {
        const nbButtonsBeforeDelete = await parametroSistemaComponentsPage.countDeleteButtons();
        await parametroSistemaComponentsPage.clickOnLastDeleteButton();

        parametroSistemaDeleteDialog = new ParametroSistemaDeleteDialog();
        expect(await parametroSistemaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Parametro Sistema?');
        await parametroSistemaDeleteDialog.clickOnConfirmButton();

        expect(await parametroSistemaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
